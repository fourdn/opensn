package com.fdn.opensn.dto

class MessageDto private constructor(
        val id: Long?,
        val sender: UserDto?,
        val receiver: ConversationDto?,
        val body: String?
) {
    data class Builder(
            var id: Long? = null,
            var sender: UserDto? = null,
            var receiver: ConversationDto? = null,
            var body: String? = null
    ){
        fun id(id: Long) = apply { this.id = id }
        fun sender(sender: UserDto) = apply { this.sender = sender }
        fun receiver(receiver: ConversationDto) = apply { this.receiver = receiver }
        fun body(body: String) = apply { this.body = body }
        fun build() = MessageDto(id, sender, receiver, body)
    }
}
