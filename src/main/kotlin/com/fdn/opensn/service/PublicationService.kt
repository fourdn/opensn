package com.fdn.opensn.service

import com.fdn.opensn.config.PublicationMessage
import com.fdn.opensn.domain.OperationSuccess
import com.fdn.opensn.domain.Publication
import com.fdn.opensn.domain.PublicationErrorMessages
import com.fdn.opensn.dto.PublicationDto
import com.fdn.opensn.dto.PublicationResponseDto
import com.fdn.opensn.repository.PublicationRepository
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PublicationService @Autowired
constructor(
    private val publicationRepository: PublicationRepository,
    private val userRepository: UserRepository
) {

  fun createPost(publicationDto: PublicationDto): PublicationResponseDto {
    // TODO make sure that user can create the publication on the target's page
    val (success, errorMessages) = validatePublicationDto(publicationDto)
    if (!success) {
      return PublicationResponseDto(success = success, errorMessages = errorMessages)
    }

    val target = userRepository.findById(publicationDto.targetId).get()
    val author = userRepository.findById(publicationDto.authorId).get()
    val publication = Publication(target, author, publicationDto.privateStatus, publicationDto.contentType,
        publicationDto.content)
    val saved = publicationRepository.save(publication)
    return PublicationResponseDto(success, "/publication/" + saved.id) // TODO take off uri to the config file
  }

  // TODO make sure that user can read the publication
  fun getPost(publicationId: Long): Publication? = publicationRepository.findByIdOrNull(publicationId)

  // TODO make sure that user can update the publication
  fun updatePost(publicationDto: PublicationDto, publicationId: Long): PublicationResponseDto? {
    val publication = publicationRepository.findByIdOrNull(publicationId) ?: return null
    var success = true
    val errorMessages = PublicationErrorMessages(
        content = kotlin.run {
          if (publicationDto.content.isNotEmpty()) ""
          else PublicationMessage.EMPTY_CONTENT.also { success = false }
        }
    )
    if (!success) {
      return PublicationResponseDto(success = success, errorMessages = errorMessages)
    }

    publication.run {
      publicationRepository.save(Publication(
          this.target,
          this.author,
          this.privateStatus,
          publicationDto.contentType,
          publicationDto.content
      ).apply { id = publication.id })
    }
    return PublicationResponseDto(success)
  }

  fun deletePost(publicationId: Long): OperationSuccess {
    val publication = publicationRepository.findByIdOrNull(publicationId) ?: return OperationSuccess.OBJECT_NOT_FOUND
    // TODO make sure that user can delete the publication
    publicationRepository.deleteById(publicationId)
    return OperationSuccess.SUCCESS
  }

  private fun validatePublicationDto(publicationDto: PublicationDto): Pair<Boolean, PublicationErrorMessages> {
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
    return Pair(success, errorMessages)
  }

}
