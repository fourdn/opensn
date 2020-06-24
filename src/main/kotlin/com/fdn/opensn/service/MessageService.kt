package com.fdn.opensn.service

import com.fdn.opensn.domain.Message
import com.fdn.opensn.domain.MessageStatus
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
            val messagesByConversationId = messageRepository.findAllByReceiver(conversation)

            val setDeliveredStatus:
                    (Message) -> Message = { Message(it.sender, it.receiver, it.body, MessageStatus.DELIVERED) }
            val deliveredMessages = messagesByConversationId.filter { it.sender != user }
                    .map(setDeliveredStatus)
            messageRepository.saveAll(deliveredMessages)

            messagesByConversationId

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
            val messageWithSender = Message(user, message.receiver, message.body, MessageStatus.SENT)
            messageRepository.save(messageWithSender)
            true
        } else {
            throw IllegalStateException("User not participate the conversation or conversation not exist")
        }
    }

    fun markMessagesAsRead(messages: List<Message>): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        val foundMessages = messageRepository.findAllByIdIn(messages.mapNotNull { it.id })
        foundMessages.ifEmpty { throw IllegalStateException("List messages is empty") }

        foundMessages[0].receiver ?: throw IllegalStateException("Receiver some messages is null")
        val receiverId = foundMessages[0].receiver?.id
                ?: throw IllegalStateException("Receiver id some messages is null")

        foundMessages.forEach {
            if (it.receiver?.id == null) {
                throw IllegalStateException("Receiver some messages is null")
            } else if (it.receiver.id != receiverId) {
                throw IllegalStateException("Receivers should be identical")
            }
        }

        val filterBySenderMessages = foundMessages.filter {
            if (it.sender?.id == null) {
                throw IllegalStateException("Sender some messages is null")
            }
            return it.sender.id != user.id
        }.map { Message(it.sender, it.receiver, it.body, MessageStatus.READ) }

        messageRepository.saveAll(filterBySenderMessages)

        return true
    }

}
