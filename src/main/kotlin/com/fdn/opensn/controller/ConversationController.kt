package com.fdn.opensn.controller

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.User
import com.fdn.opensn.domain.UserPrincipal
import com.fdn.opensn.dto.ConversationDto
import com.fdn.opensn.dto.UserDto
import com.fdn.opensn.service.ConversationService
import com.fdn.opensn.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.lang.IllegalStateException

@RestController
@RequestMapping("/conversation")
class ConversationController @Autowired
constructor(
        private val conversationService: ConversationService,
        private val userService: UserService
) {
    @GetMapping("/get-all-my")
    fun getMyConversations(): ResponseEntity<List<ConversationDto>> {
        return try {
            ResponseEntity(conversationService.getAllUserConversations().map {
                ConversationDto.Builder().apply {
                    id(it.id ?: -1)
                }.build()
            }, HttpStatus.OK)
        } catch (e: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/get-all-users")
    fun getAllUsers(): List<UserDto> = userService.getAllUsers().map {
        UserDto.Builder().apply {
            id(it.id)
            username(it.username)
        }.build()
    }

    @PostMapping("/create")
    fun createConversation(@RequestBody users: List<UserDto>): ResponseEntity<Void> {
        return if (conversationService.createConversation(users)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
