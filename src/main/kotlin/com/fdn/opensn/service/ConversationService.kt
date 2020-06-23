package com.fdn.opensn.service

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.User
import com.fdn.opensn.domain.UserPrincipal
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
    fun getAllUserConversations(userPrincipal: UserPrincipal): List<Conversation> {
        val user = userRepository.findByUsername(userPrincipal.username)
                ?: throw IllegalStateException("User does not exist")
        return conversationRepository.findAllByUsersContains(user)
    }

    fun createConversation(users: List<User>): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.findByUsername(authentication.name)
                ?: throw IllegalStateException("User does not exist")
        val conversation = Conversation(setOf(*users.toTypedArray(), user))
        conversationRepository.save(conversation)
        // TODO return false if trying to create existing 2 user conversation
        return true
    }
}
