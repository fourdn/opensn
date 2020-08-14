package com.fdn.opensn.dto

class UserDto private constructor(
        val id: Long?,
        val username: String?,
        val password: String?
){
    data class Builder(
            var id: Long? = null,
            var username: String? = null,
            var password: String? = null
    ){
        fun id(id: Long) = apply { this.id = id }
        fun username(username: String) = apply { this.username = username }
        fun password(password: String) = apply { this.password = password }
        fun build() = UserDto(id, username, password)
    }

}
