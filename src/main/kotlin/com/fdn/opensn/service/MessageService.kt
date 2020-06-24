package com.fdn.opensn.service

import com.fdn.opensn.domain.Message
import com.fdn.opensn.repository.ConversationRepository
import com.fdn.opensn.repository.MessageRepository
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class MessageService @Autowired
constructor(
        private val messageRepository: MessageRepository,
        private val conversationService: ConversationService,
        private val conversationRepository: ConversationRepository,
        private val userRepository: UserRepository
) {
    fun getAllMessagesByConversationId(conversationId: Long): List<Message> {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")


        val conversation = conversationRepository.findByIdOrNull(conversationId)
                ?: throw IllegalStateException("Conversation not exist")
        return if (conversationService.containsUserInConversation(conversationId, user)) {
            messageRepository.findAllByReceiver(conversation)
        } else {
            throw IllegalStateException("User not participate the conversation")
        }

    }

    fun sendMessage(message: Message): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        message.receiver ?: throw IllegalStateException("Receiver is null")
        val receiverId = message.receiver.id ?: throw IllegalStateException("Receiver id is null")

        return if (conversationService.isConversationExist(receiverId)
                && conversationService.containsUserInConversation(receiverId, user)) {
            val messageWithSender = Message(user, message.receiver, message.body)
            messageRepository.save(messageWithSender)
            true
        } else {
            throw IllegalStateException("User not participate the conversation or conversation not exist")
        }
    }

}
