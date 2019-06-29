package com.fdn.opensn.service

import com.fdn.opensn.domain.UserPrincipal
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserDetailsServiceImpl @Autowired
constructor(private val userRepository: UserRepository) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    val user = userRepository.findByUsername(username)
    return if (user != null) {
      UserPrincipal(user)
    } else {
      throw UsernameNotFoundException("User \"$username\" does not exist")
    }
  }

}
