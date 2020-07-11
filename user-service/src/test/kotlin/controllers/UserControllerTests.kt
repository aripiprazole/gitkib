package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.mockito.Mockito.*
import java.util.*

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

private const val INDEX_URL = "/users"
private const val STORE_URL = "/users"
private const val SHOW_URL = "/users/%s"
private const val UPDATE_URL = "/users/%s"
private const val DESTROY_URL = "/users/%s"

@SpringBootTest
@AutoConfigureMockMvc
// TODO: use a faker library
class UserControllerTests {

  @MockBean
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  fun `test should show users paginated when GET UserController@index`() {
    val users = listOf<User>()

    val page: Page<User> = PageImpl(
      users,
      Pageable.unpaged(),
      users.size.toLong()
    )

    every(userRepository.findAll(any(Pageable::class.java))).thenReturn(page)

    mockMvc.perform(get(INDEX_URL).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(userRepository, times(1)).findAll(any(Pageable::class.java))
  }

  @Test
  fun `test should show user that have id 1 when GET UserController@show with id path variable 1`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password"
    )

    val (id) = user

    every(userRepository.findById(id)).thenReturn(Optional.of(user))

    mockMvc.perform(get(SHOW_URL.format(id)).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userRepository, times(1)).findById(id)
  }

  @Test
  fun `test should store user in database and return that in the http response`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password"
    )

    val body = UserCreateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every(userRepository.save(any(User::class.java))).thenReturn(user)

    mockMvc.perform(post(STORE_URL)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userRepository, times(1)).save(any(User::class.java))
  }

}
