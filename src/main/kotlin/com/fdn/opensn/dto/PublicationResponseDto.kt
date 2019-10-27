package com.fdn.opensn.dto

import com.fdn.opensn.domain.PublicationErrorMessages

class PublicationResponseDto(
    val success: Boolean,
    val errorMessages: PublicationErrorMessages
)
