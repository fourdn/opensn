package com.fdn.opensn.dto

import javax.persistence.*

class ConversationDto private constructor(
        val id: Long?,
        val users: Set<UserDto>?,
        val messages: List<MessageDto>?
) {
    data class Builder(
            var id: Long? = null,
            var users: Set<UserDto>? = null,
            var messages: List<MessageDto>? = null
    ) {
        fun id(id: Long) = apply { this.id = id }
        fun users(users: Set<UserDto>) = apply { this.users = users }
        fun messages(messages: List<MessageDto>) = apply { this.messages = messages }
        fun build() = ConversationDto(id, users, messages)
    }
}
