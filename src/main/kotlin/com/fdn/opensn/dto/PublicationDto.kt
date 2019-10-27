package com.fdn.opensn.dto

import com.fdn.opensn.domain.ContentType
import com.fdn.opensn.domain.PrivateStatus

class PublicationDto(
    val targetId: Long,
    val authorId: Long,
    val privateStatus: PrivateStatus,
    val contentType: ContentType,
    val content: String
)
