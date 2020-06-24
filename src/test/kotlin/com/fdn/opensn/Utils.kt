package com.fdn.opensn

import com.fdn.opensn.dto.UserDto
import io.restassured.RestAssured
import io.restassured.response.Response

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
