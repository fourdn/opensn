package com.fdn.opensn.controller

import com.fdn.opensn.dto.PublicationDto
import com.fdn.opensn.dto.PublicationResponseDto
import com.fdn.opensn.service.PublicationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

}
