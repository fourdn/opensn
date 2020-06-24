package com.fdn.opensn.repository

import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface ConversationRepository : JpaRepository<Conversation, Long> {
    fun findAllByUsersContains(user: User): List<Conversation>

    fun existsConversationByIdAndUsersContains(id: Long, user: User): Boolean

    fun existsConversationById(id: Long): Boolean

    fun existsConversationByUsersEquals(users: Set<User>): Boolean
}
