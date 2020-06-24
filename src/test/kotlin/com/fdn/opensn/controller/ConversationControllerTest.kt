package com.fdn.opensn.controller

import com.fdn.opensn.OpensnApplication
import com.fdn.opensn.Utils
import com.fdn.opensn.domain.Conversation
import com.fdn.opensn.dto.UserDto
import com.fdn.opensn.repository.UserRepository
import io.restassured.RestAssured
import io.restassured.response.Response
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
        createTwoUsersAndConversationWithThem("createConversationTest")
    }

    @Test
    fun getAllUserConversationsTest() {
        val (firstUserId, secondUserId, authResponse) = createTwoUsersAndConversationWithThem("getAllUserConversationsTest")

        val getAllUserConversationResponse = RestAssured.given().apply {
            header("Content-Type", "application/json")
            cookies(authResponse.detailedCookies)
            body(listOf(UserIdDto(secondUserId)))
        }.run { get("/conversation/get-all-my") }
        assertEquals(HttpStatus.OK.value(), getAllUserConversationResponse.statusCode)

        val jsonPath = getAllUserConversationResponse.jsonPath()
        val userConversations = jsonPath.getList("", Conversation::class.java)
        assertEquals(1, userConversations.size)
        assertEquals(2, userConversations[0].users.size)
        assertEquals(setOf(firstUserId, secondUserId), userConversations[0].users.map { it.id }.toSet())
    }

    private class UserIdDto(val id: Long)

    private data class UserPairAndAuthResponse(val firstUserId: Long, val secondUserId: Long, val authResponse: Response)

    private fun createTwoUsersAndConversationWithThem(userDataPrefix: String): UserPairAndAuthResponse {
        val firstUserDto = UserDto("${userDataPrefix}First", "${userDataPrefix}First")
        val registerFirstUserResponse = Utils.sendRegistrationRequest(firstUserDto)
        assertEquals(registerFirstUserResponse.statusCode, HttpStatus.OK.value())

        val secondUserDto = UserDto("${userDataPrefix}Second", "${userDataPrefix}Second")
        val registerSecondUserResponse = Utils.sendRegistrationRequest(secondUserDto)
        assertEquals(registerSecondUserResponse.statusCode, HttpStatus.OK.value())

        val firstUserId = userRepository?.findByUsername("${userDataPrefix}First")?.id
                ?: throw IllegalStateException("The user can not be null")
        val secondUserId = userRepository.findByUsername("${userDataPrefix}Second")?.id
                ?: throw IllegalStateException("The user can not be null")

        val authResponse = Utils.sendAuthorizationRequest(firstUserDto)
        assertEquals(authResponse.statusCode, HttpStatus.OK.value())

        val createConversationResponse = RestAssured.given().apply {
            header("Content-Type", "application/json")
            cookies(authResponse.detailedCookies)
            body(listOf(UserIdDto(secondUserId)))
        }.run { post("/conversation/create") }
        assertEquals(createConversationResponse.statusCode, HttpStatus.OK.value())

        return UserPairAndAuthResponse(firstUserId, secondUserId, authResponse)
    }
}
