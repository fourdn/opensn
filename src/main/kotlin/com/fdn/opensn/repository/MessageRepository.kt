package com.fdn.opensn.repository

import com.fdn.opensn.domain.Message
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long> {
}
