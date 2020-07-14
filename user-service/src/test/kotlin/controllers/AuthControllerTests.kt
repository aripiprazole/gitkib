package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.bodies.UserAuthenticateBody
import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.controllers.AuthController.Companion.AUTHENTICATE_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.AuthController.Companion.REGISTER_ENDPOINT
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.hamcrest.Matchers.any
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.Mockito.*
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.password.PasswordEncoder

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

@SpringBootTest
@AutoConfigureMockMvc
// TODO: use a faker library
class AuthControllerTests {

  @MockBean
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Autowired
  private lateinit var passwordEncoder: PasswordEncoder

  @Test
  fun `test should login correctly and send the jwt token in the http response`() {
    val password = "fake password"

    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = passwordEncoder.encode(password),
      roles = mutableSetOf()
    )

    every(userRepository.findByUsername(user.username)).thenReturn(user)

    val body = UserAuthenticateBody(
      username = user.username,
      password = password
    )

    mockMvc.perform(post(AUTHENTICATE_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(jsonPath("token", any(String::class.java)))
  }

  @Test
  fun `test should store user in database and return that in the http response when POST UserController@store with REGISTER_ENDPOINT`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password",
      roles = mutableSetOf()
    )

    val body = UserCreateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every(userRepository.save(Mockito.any(User::class.java))).thenReturn(user)

    mockMvc.perform(post(REGISTER_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userRepository, times(1)).save(Mockito.any(User::class.java))
  }

}
