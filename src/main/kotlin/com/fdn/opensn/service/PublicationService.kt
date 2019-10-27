package com.fdn.opensn.service

import com.fdn.opensn.config.PublicationMessage
import com.fdn.opensn.domain.Publication
import com.fdn.opensn.domain.PublicationErrorMessages
import com.fdn.opensn.dto.PublicationDto
import com.fdn.opensn.dto.PublicationResponseDto
import com.fdn.opensn.repository.PublicationRepository
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PublicationService @Autowired
constructor(
    private val publicationRepository: PublicationRepository,
    private val userRepository: UserRepository
) {

  fun createPost(publicationDto: PublicationDto): PublicationResponseDto {
    // TODO("Unimplemented")
    var success = true
    // TODO handle the contentType and privateStatus errors
    val errorMessages = PublicationErrorMessages(
        targetId = kotlin.run {
          if (userRepository.existsById(publicationDto.targetId)) ""
          else PublicationMessage.TARGET_NOT_EXISTS.also { success = false }
        },
        authorId = kotlin.run {
          if (userRepository.existsById(publicationDto.authorId)) ""
          else PublicationMessage.AUTHOR_NOT_EXISTS.also { success = false }
        },
        content = kotlin.run {
          if (publicationDto.content.isNotEmpty()) ""
          else PublicationMessage.EMPTY_CONTENT.also { success = false }
        }
    )
    if (!success) {
      return PublicationResponseDto(success = success, errorMessages = errorMessages)
    }

    val target = userRepository.findById(publicationDto.targetId).get()
    val author = userRepository.findById(publicationDto.authorId).get()
    val publication = Publication(target, author, publicationDto.privateStatus, publicationDto.contentType)
    val saved = publicationRepository.save(publication)
    return PublicationResponseDto(success, "/publication/" + saved.id) // TODO take off uri to the config file
  }

}
