package com.fdn.opensn.domain

import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserPrincipal(user: User) : org.springframework.security.core.userdetails.User(
    user.username,
    user.password,
    user.roles.toList().map { SimpleGrantedAuthority(it.toString()) }
)
