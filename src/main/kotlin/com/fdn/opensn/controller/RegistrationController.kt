package com.fdn.opensn.controller

import com.fdn.opensn.dto.RegistrationResponseDto
import com.fdn.opensn.dto.UserDto
import com.fdn.opensn.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/registration")
class RegistrationController @Autowired
constructor(private val registrationService: RegistrationService) {

  @PostMapping
  fun register(@RequestBody userDto: UserDto): RegistrationResponseDto {
    return registrationService.registerUser(userDto)
  }

}
