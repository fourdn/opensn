package com.fdn.opensn.service

import com.fdn.opensn.repository.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageService @Autowired
constructor(
        private val messageRepository: MessageRepository
) {

}
