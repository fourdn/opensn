package com.fdn.opensn.dto

import com.fdn.opensn.domain.ContentType
import com.fdn.opensn.domain.PrivateStatus

class PublicationDto(
    val targetId: Long = 0,
    val authorId: Long = 0,
    val privateStatus: PrivateStatus = PrivateStatus.PRIVATE,
    val contentType: ContentType = ContentType.TEXT,
    val content: String = ""
)
