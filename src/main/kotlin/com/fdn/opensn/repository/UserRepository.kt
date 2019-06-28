package com.fdn.opensn.repository

import com.fdn.opensn.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

  fun findByUsername(username: String): User?

}
