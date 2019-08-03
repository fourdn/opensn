package com.fdn.opensn.auth

import com.fdn.opensn.OpensnApplication
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
class AuthorizationTest {

  @Test
  fun registerAndAuthorizeTest() {
    val userDto = UserDto("registerAndAuthorizeTest", "registerAndAuthorizeTest")
    val registrationResponse = RegistrationTest.sendRegistrationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, registrationResponse.statusCode)
    assertEquals(true, registrationResponse.jsonPath().getBoolean("success"))

    val authorizationResponse = sendAuthorizationRequest(userDto)
    assertEquals(HttpServletResponse.SC_OK, authorizationResponse.statusCode)
  }

  @Test
  fun loginWithWrongCredentialsTest() {
    val userDto = UserDto("loginWithWrongCredentialsTest", "loginWithWrongCredentialsTest")
    val authorizationResponse = sendAuthorizationRequest(userDto)
    assertEquals(HttpServletResponse.SC_FORBIDDEN, authorizationResponse.statusCode)
  }

  companion object {
    fun sendAuthorizationRequest(userDto: UserDto): Response {
      val request = RestAssured.given().apply {
        param("username", userDto.username)
        param("password", userDto.password)
      }
      return request.post("/login")
    }
  }

}
