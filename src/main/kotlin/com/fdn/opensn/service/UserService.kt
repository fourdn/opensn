package com.fdn.opensn.service

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.User
import com.fdn.opensn.repository.ConversationRepository
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired
constructor(
        private val userRepository: UserRepository
) {
    fun getAllUsers() = userRepository.findAll()
}
