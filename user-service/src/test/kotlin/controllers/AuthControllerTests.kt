package com.lorenzoog.gitkib.userservice.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.bodies.UserAuthenticateBody
import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.controllers.AuthController.Companion.AUTHENTICATE_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.AuthController.Companion.REGISTER_ENDPOINT
import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.entities.Role
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.security.auth.AUTHENTICATION_HEADER
import com.lorenzoog.gitkib.userservice.services.UserProvider
import com.lorenzoog.gitkib.userservice.utils.mock
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.any
import org.jetbrains.exposed.sql.SizedCollection
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant
import java.util.*

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

@SpringBootTest
@AutoConfigureMockMvc
// TODO: use a faker library
class AuthControllerTests {

  @Value("\${jwt.issuer}")
  private lateinit var jwtIssuer: String

  @Value("\${jwt.secret}")
  private lateinit var jwtSecret: String

  @MockBean
  private lateinit var userProvider: UserProvider

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  fun `test should login correctly and send the jwt token in the http response`() = runBlocking {
    val password = "fake password"

    val user = User.mock().apply {
      this.password = password
    }

    every(userProvider.findByUsername(user.username)).thenReturn(user)

    val body = UserAuthenticateBody(
      username = user.username,
      password = password
    )

    mockMvc.perform(post(AUTHENTICATE_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(jsonPath("token", any(String::class.java)))

    Unit
  }

  @Test
  fun `test should store user in database and return that in the http response when POST UserController@store with REGISTER_ENDPOINT`() = runBlocking {
    val user = User.mock()

    val body = UserCreateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every(userProvider.save(any())).thenReturn(user)

    mockMvc.perform(post(REGISTER_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userProvider, times(1)).save(any())

    Unit
  }

  @Test
  fun `test should view users of database and return that in the http response when GET UserController@index and the requester is authenticated`() = runBlocking {
    val user = User.mock {
      roles = SizedCollection(Role.mock {
        privileges = SizedCollection(Privilege.mock {
          name = Privilege.VIEW_USER
        })
      })
    }

    val now = Instant.now()
    val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)

    val jwtToken =
      JWT.create()
        .withSubject(user.username)
        .withIssuedAt(Date.from(now))
        .withIssuer(jwtIssuer)
        .withExpiresAt(Date.from(now.plusMillis(JWT_EXPIRES_AT)))
        .sign(jwtAlgorithm)

    every(userProvider.findByUsername(user.username)).thenReturn(user)

    val users = listOf(
      User.mock(),
      User.mock(),
      user
    )

    val page: Page<User> = PageImpl(
      users,
      Pageable.unpaged(),
      users.size.toLong()
    )

    every(userProvider.findAll(page = 0, offset = USER_PAGINATION_OFFSET)).thenReturn(page)

    mockMvc.perform(get(UserController.INDEX_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .header(AUTHENTICATION_HEADER, "Bearer $jwtToken"))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(userProvider, times(1)).findAll(page = 0, offset = USER_PAGINATION_OFFSET)

    Unit
  }

}
