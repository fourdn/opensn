package com.fdn.opensn

import com.fdn.opensn.domain.User
import com.fdn.opensn.domain.UserRole
import com.fdn.opensn.repository.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.Assert

@RunWith(SpringRunner::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  @Autowired
  private var userRepository: UserRepository? = null

  @Test
  fun saveAndReceiveTest() {
    val user = createDemoUser()
    val savedUser = saveUser(user).also { Assert.notNull(it.id, "Saved user id must be not null") }
    val receivedUser = getUserById(savedUser.id!!)
        .also { Assert.notNull(it, "Received user must be not null") }

    assertEquals(user.username, receivedUser!!.username)
    assertEquals(user.password, receivedUser.password)
    assertEquals(user.roles.size, receivedUser.roles.size)
  }

  private fun createDemoUser() = User("demoUsername", "demoPassword", setOf(UserRole.USER))

  private fun saveUser(user: User) = userRepository?.save(user) ?: throw RuntimeException("UserRepository is null!")

  private fun getUserById(userId: Long) = userRepository?.findByIdOrNull(userId)

}
