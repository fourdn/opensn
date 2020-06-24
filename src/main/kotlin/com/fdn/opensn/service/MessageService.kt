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
        private val userRepository: UserRepository
) {
    fun getAllMessagesByConversation(conversation: Conversation): List<Message> {
        //TODO check that user participate in conversation
        return messageRepository.findAllByReceiver(conversation);
    }

    fun sendMessage(message: Message): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        val checkedMessage = Message(user, message.receiver, message.body)
        //TODO check that conversation exist
        //TODO check that user participate in conversation
        messageRepository.save(checkedMessage)
        return true
    }

}
