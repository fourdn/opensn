package com.fdn.opensn.controller

import com.fdn.opensn.dto.MessageDto
import com.fdn.opensn.dto.UserDto
import com.fdn.opensn.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/message")
class MessageController @Autowired
constructor(
        private val messageService: MessageService
) {
    @GetMapping("/get-by-conversation")
    fun getMessagesByConversation(@RequestParam conversationId: Long): ResponseEntity<List<MessageDto>> {
        return try {
            ResponseEntity(messageService.getAllMessagesByConversationId(conversationId).map {
                MessageDto.Builder().apply {
                    id(it.id ?: -1)
                    sender(UserDto.Builder().apply {
                        id(it.sender?.id ?: -1)
                        username(it.sender?.username ?: "")
                    }.build())
                    body(it.body)
                }.build()
            }, HttpStatus.OK)
        } catch (e: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: MessageDto): ResponseEntity<Void> {
        return if (messageService.sendMessage(message)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/read")
    fun markMessageAsRead(@RequestBody messages: List<MessageDto>): ResponseEntity<Void> {
        return if (messageService.markMessagesAsRead(messages)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
