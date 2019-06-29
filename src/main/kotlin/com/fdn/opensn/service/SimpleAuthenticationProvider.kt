package com.fdn.opensn.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class SimpleAuthenticationProvider @Autowired
constructor(
    private val userDetailsService: UserDetailsServiceImpl
) : AuthenticationProvider {

  override fun authenticate(authentication: Authentication): Authentication? {
    val username = authentication.name
    val password = authentication.credentials.toString()

    val userDetails = userDetailsService.loadUserByUsername(username)

    return if (userDetails.password == password) {
      UsernamePasswordAuthenticationToken(userDetails.username, userDetails.password, userDetails.authorities)
    } else {
      throw BadCredentialsException("Authentication failed")
    }
  }

  override fun supports(authentication: Class<*>): Boolean {
    return authentication == UsernamePasswordAuthenticationToken::class
  }

}
