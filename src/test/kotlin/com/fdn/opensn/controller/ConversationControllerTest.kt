package com.fdn.opensn.controller

import com.fdn.opensn.OpensnApplication
import com.fdn.opensn.Utils
import com.fdn.opensn.dto.UserDto
import com.fdn.opensn.repository.UserRepository
import io.restassured.RestAssured
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import java.lang.IllegalStateException

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = [OpensnApplication::class])
class ConversationControllerTest {

    @Autowired
    private val userRepository: UserRepository? = null

    @Test
    fun createConversationTest() {
        val firstUserDto = UserDto("createConversationTestFirst", "createConversationTestFirst")
        val registerFirstUserResponse = Utils.sendRegistrationRequest(firstUserDto)
        assertEquals(registerFirstUserResponse.statusCode, HttpStatus.OK.value())

        val secondUserDto = UserDto("createConversationTestSecond", "createConversationTestSecond")
        val registerSecondUserResponse = Utils.sendRegistrationRequest(secondUserDto)
        assertEquals(registerSecondUserResponse.statusCode, HttpStatus.OK.value())

        val secondUserId = userRepository?.findByUsername("createConversationTestSecond")?.id ?:
                throw IllegalStateException("The user can not be null")

        val authResponse = Utils.sendAuthorizationRequest(firstUserDto)
        assertEquals(authResponse.statusCode, HttpStatus.OK.value())

        val createConversationResponse = RestAssured.given().apply {
            header("Content-Type", "application/json")
            cookies(authResponse.detailedCookies)
            body(listOf(UserIdDto(secondUserId)))
        }.run { post("/conversation/create") }
        assertEquals(createConversationResponse.statusCode, HttpStatus.OK.value())
    }

    private class UserIdDto(val id: Long)
}
