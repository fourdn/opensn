package com.fdn.opensn.service

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.Message
import com.fdn.opensn.repository.MessageRepository
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class MessageService @Autowired
constructor(
        private val messageRepository: MessageRepository,
        private val conversationService: ConversationService,
        private val userRepository: UserRepository
) {
    fun getAllMessagesByConversation(conversation: Conversation): List<Message> {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        return if (conversationService.isConversationExist(conversation) // check id, so id not null
                && conversationService.containsUserInConversation(conversation.id!!, user)) {
            messageRepository.findAllByReceiver(conversation)
        } else {
            throw IllegalStateException("User not participate the conversation or conversation not exist")
        }

    }

    fun sendMessage(message: Message): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        return if (message.receiver != null
                && conversationService.isConversationExist(message.receiver) // check id, so id not null
                && conversationService.containsUserInConversation(message.receiver.id!!, user)) {
            val messageWithSender = Message(user, message.receiver, message.body)
            messageRepository.save(messageWithSender)
            true
        } else {
            throw IllegalStateException("User not participate the conversation or conversation not exist")
        }
    }

}
