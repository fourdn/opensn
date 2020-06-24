package com.fdn.opensn.controller

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.User
import com.fdn.opensn.domain.UserPrincipal
import com.fdn.opensn.service.ConversationService
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
        private val conversationService: ConversationService
) {
    @GetMapping("/get-all-my")
    fun getMyConversations(): ResponseEntity<List<Conversation>> {
        return try {
            ResponseEntity(conversationService.getAllUserConversations(), HttpStatus.OK)
        } catch (e: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/create")
    fun createConversation(@RequestBody users: List<User>): ResponseEntity<Void> {
        return if (conversationService.createConversation(users)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
