package com.fdn.opensn.service

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.User
import com.fdn.opensn.repository.ConversationRepository
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class ConversationService @Autowired
constructor(
        private val conversationRepository: ConversationRepository,
        private val userRepository: UserRepository
) {
    fun getAllUserConversations(): List<Conversation> {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        return conversationRepository.findAllByUsersContains(user)
    }

    fun createConversation(users: List<User>): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")

        val conversation = Conversation(setOf(*users.toTypedArray(), user))

        return if (!conversationRepository.existsConversationByUsersEquals(conversation.users)) {
            conversationRepository.save(conversation)
            true
        } else {
            throw IllegalStateException("2 user conversation already exist")
        }
    }

    fun containsUserInConversation(conversationId: Long, user: User): Boolean {
        return conversationRepository.existsConversationByIdAndUsersContains(conversationId, user)
    }

    fun isConversationExist(conversationId: Long): Boolean {
        return conversationRepository.existsConversationById(conversationId)
    }
}
