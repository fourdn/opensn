package com.fdn.opensn

import com.fdn.opensn.dto.UserDto
import io.restassured.RestAssured
import io.restassured.response.Response
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = [OpensnApplication::class])
class Utils {
    companion object {
        fun sendRegistrationRequest(userDto: UserDto): Response {
            val request = RestAssured.given().apply {
                header("Content-Type", "application/json")
                body(userDto)
            }
            return request.post("/registration")
        }

        fun sendAuthorizationRequest(userDto: UserDto): Response {
            val request = RestAssured.given().apply {
                param("username", userDto.username)
                param("password", userDto.password)
            }
            return request.post("/login")
        }
    }
}
