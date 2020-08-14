package com.fdn.opensn.repository

import com.fdn.opensn.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {

    @Query("""
    select user from User user 
    left join fetch user.roles
    where user.username like :username
  """)
    fun findByUsername(username: String): User?

    @Query("""
    select user from User user 
    left join fetch user.roles
  """)
    override fun findAll(): List<User>

}
