package com.fdn.opensn.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class PublicationErrorMessages(
    val targetId: String,
    val authorId: String,
    val privateStatus: String,
    val contentType: String,
    val content: String
)
