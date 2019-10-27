package com.fdn.opensn.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fdn.opensn.domain.PublicationErrorMessages

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class PublicationResponseDto(
    val success: Boolean = false,
    val uri: String = "",
    val errorMessages: PublicationErrorMessages = PublicationErrorMessages()
)
