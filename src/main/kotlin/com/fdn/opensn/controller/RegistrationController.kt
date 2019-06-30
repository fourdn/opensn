package com.fdn.opensn.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import com.fdn.opensn.dto.RegistrationResponseDto
import com.fdn.opensn.dto.UserDto
import org.springframework.web.bind.annotation.PostMapping
import com.fdn.opensn.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/registration")
class RegistrationController @Autowired
constructor(private val registrationService: RegistrationService) {

  @PostMapping
  fun register(@RequestBody userDto: UserDto): RegistrationResponseDto {
    return registrationService.registerUser(userDto)
  }

}
