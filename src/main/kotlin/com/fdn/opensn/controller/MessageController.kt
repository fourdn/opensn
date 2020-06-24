package com.fdn.opensn.controller

import com.fdn.opensn.domain.Message
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
    fun getMessagesByConversation(@RequestParam conversationId: Long): ResponseEntity<List<Message>> {
        return try {
            ResponseEntity(messageService.getAllMessagesByConversationId(conversationId), HttpStatus.OK)
        } catch (e: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/send")
    fun sendMessage(@RequestBody message: Message): ResponseEntity<Void> {
        return if (messageService.sendMessage(message)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }
}
