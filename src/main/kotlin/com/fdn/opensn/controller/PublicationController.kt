package com.fdn.opensn.controller

import com.fdn.opensn.domain.OperationSuccess
import com.fdn.opensn.domain.Publication
import com.fdn.opensn.dto.PublicationDto
import com.fdn.opensn.dto.PublicationResponseDto
import com.fdn.opensn.service.PublicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/publication")
class PublicationController @Autowired
constructor(private val publicationService: PublicationService) {

  @PostMapping
  fun createPublication(@RequestBody publicationDto: PublicationDto): ResponseEntity<PublicationResponseDto> {
    val responseDto = publicationService.createPost(publicationDto)
    return if (responseDto.success) ResponseEntity.created(URI(responseDto.uri)).body(responseDto)
    else ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto)
    // TODO return FORBIDDEN if permission denied
  }

  @GetMapping("/{publicationId}")
  fun getPublication(@PathVariable publicationId: Long): ResponseEntity<Publication> {
    val publication = publicationService.getPost(publicationId) ?: return ResponseEntity.notFound().build()
    return ResponseEntity.ok(publication)
    // TODO return FORBIDDEN if permission denied
  }

  @PatchMapping("/{publicationId}")
  fun updatePublication(
      @PathVariable publicationId: Long,
      @RequestBody publicationDto: PublicationDto
  ): ResponseEntity<PublicationResponseDto> {
    val responseDto = publicationService.updatePost(publicationDto, publicationId)
        ?: return ResponseEntity.notFound().build()
    return if (responseDto.success) ResponseEntity.ok(responseDto)
    else ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto)
    // TODO return FORBIDDEN if permission denied
  }

  @DeleteMapping("/{publicationId}")
  fun deletePublication(@PathVariable publicationId: Long) = when (publicationService.deletePost(publicationId)) {
      OperationSuccess.SUCCESS -> ResponseEntity.ok()
      OperationSuccess.OBJECT_NOT_FOUND -> ResponseEntity.notFound()
      OperationSuccess.PERMISSION_DENIED -> ResponseEntity.status(HttpStatus.FORBIDDEN)
      OperationSuccess.BAD_REQUEST -> ResponseEntity.badRequest()
  }.apply { build<String>() }

}
