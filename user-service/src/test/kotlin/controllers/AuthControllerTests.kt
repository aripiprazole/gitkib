package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.hamcrest.Matchers.any
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.Mockito.*

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

private const val LOGIN_URL = "/login"

@SpringBootTest
@AutoConfigureMockMvc
// TODO: use a faker library
class AuthControllerTests {

  @MockBean
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  fun `test should login correctly and send the jwt token in the http response`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password"
    )

    every(userRepository.findByUsername(user.username)).thenReturn(user)

    val body = mapOf(
      "username" to user.username,
      "password" to user.password
    )

    mockMvc.perform(post(LOGIN_URL).content(objectMapper.writeValueAsString(body)))
      .andExpect(jsonPath("token", any(String::class.java)))
  }

}
