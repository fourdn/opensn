package com.fdn.opensn.service

import com.fdn.opensn.config.RegistrationCode
import com.fdn.opensn.config.RegistrationMessage
import com.fdn.opensn.domain.User
import com.fdn.opensn.domain.UserRole
import com.fdn.opensn.dto.RegistrationResponseDto
import com.fdn.opensn.dto.UserDto
import com.fdn.opensn.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationService @Autowired
constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

  fun registerUser(userDto: UserDto): RegistrationResponseDto {
    val receivedUser = userRepository.findByUsername(userDto.username)
    if (receivedUser != null) {
      return RegistrationResponseDto(false, RegistrationMessage.USERNAME_TAKEN, RegistrationCode.USERNAME_TAKEN)
    }

    if (userDto.username.length < 3) {
      return RegistrationResponseDto(false, RegistrationMessage.USERNAME_SHORT, RegistrationCode.USERNAME_SHORT)
    }
    if (userDto.password.length < 3) {
      return RegistrationResponseDto(false, RegistrationMessage.PASSWORD_SHORT, RegistrationCode.PASSWORD_SHORT)
    }

    val newUser = User(userDto.username, passwordEncoder.encode(userDto.password), hashSetOf(UserRole.USER))

    userRepository.save(newUser)

    return RegistrationResponseDto(true, RegistrationMessage.SUCCESS, RegistrationCode.SUCCESS)
  }

}
