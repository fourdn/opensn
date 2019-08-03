package com.fdn.opensn.auth

import com.fdn.opensn.OpensnApplication
import com.fdn.opensn.config.RegistrationCode
import com.fdn.opensn.config.RegistrationMessage
import com.fdn.opensn.dto.UserDto
import io.restassured.RestAssured
import io.restassured.response.Response
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.servlet.http.HttpServletResponse

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = [OpensnApplication::class])
class RegistrationTest {

  @Test
  fun registerUserTest() {
    val userDto = UserDto("registerUserTest", "registerUserTest")
    val response = sendRegistrationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, response.statusCode)

    val jsonPath = response.jsonPath()
    assertEquals(true, jsonPath.getBoolean("success"))
    assertEquals("You've been successfully registered", jsonPath.getString("message"))
    assertEquals(0, jsonPath.getInt("statusCode"))
  }

  @Test
  fun usernameAlreadyTakenTest() {
    val userDto = UserDto("usernameAlreadyTakenTest", "usernameAlreadyTakenTest")
    val response = sendRegistrationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, response.statusCode)
    assertEquals(RegistrationCode.SUCCESS, response.jsonPath().getInt("statusCode"))

    val response2 = sendRegistrationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, response2.statusCode)
    val jsonPath = response2.jsonPath()
    assertEquals(RegistrationCode.USERNAME_TAKEN, jsonPath.getInt("statusCode"))
    assertEquals(RegistrationMessage.USERNAME_TAKEN, jsonPath.getString("message"))
    assertEquals(false, jsonPath.getBoolean("success"))
  }

  @Test
  fun usernameTooShortTest() {
    val userDto = UserDto("u", "usernameTooShortTest")
    val response = sendRegistrationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, response.statusCode)

    val jsonPath = response.jsonPath()
    assertEquals(RegistrationCode.USERNAME_SHORT, jsonPath.getInt("statusCode"))
    assertEquals(RegistrationMessage.USERNAME_SHORT, jsonPath.getString("message"))
    assertEquals(false, jsonPath.getBoolean("success"))
  }

  @Test
  fun passwordTooShortTest() {
    val userDto = UserDto("passwordTooShortTest", "p")
    val response = sendRegistrationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, response.statusCode)

    val jsonPath = response.jsonPath()
    assertEquals(RegistrationCode.PASSWORD_SHORT, jsonPath.getInt("statusCode"))
    assertEquals(RegistrationMessage.PASSWORD_SHORT, jsonPath.getString("message"))
    assertEquals(false, jsonPath.getBoolean("success"))
  }

  companion object {
    fun sendRegistrationRequest(userDto: UserDto): Response {
      val request = RestAssured.given().apply {
        header("Content-Type", "application/json")
        body(userDto)
      }
      return request.post("/registration")
    }
  }

}
