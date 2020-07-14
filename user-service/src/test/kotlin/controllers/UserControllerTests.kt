package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.config.SecurityTestConfig
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.DESTROY_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.INDEX_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.SHOW_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.STORE_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.UPDATE_ENDPOINT
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
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.mockito.Mockito.*
import org.springframework.security.test.context.support.WithUserDetails
import java.util.*

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

@SpringBootTest(
  classes = [SecurityTestConfig::class]
)
@AutoConfigureMockMvc
// TODO: use a faker library
class UserControllerTests {

  @MockBean
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  @WithUserDetails("just-logged")
  fun `test should show users paginated when GET UserController@index`() {
    val users = listOf<User>()

    val page: Page<User> = PageImpl(
      users,
      Pageable.unpaged(),
      users.size.toLong()
    )

    every(userRepository.findAll(any(Pageable::class.java))).thenReturn(page)

    mockMvc.perform(get(INDEX_ENDPOINT).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(userRepository, times(1)).findAll(any(Pageable::class.java))
  }

  @Test
  @WithUserDetails("just-logged")
  fun `test should show user that have id 1 when GET UserController@show with id path variable 1`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password",
      roles = mutableSetOf()
    )

    val (id) = user

    every(userRepository.findById(id)).thenReturn(Optional.of(user))

    mockMvc.perform(get(SHOW_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userRepository, times(1)).findById(id)
  }

  @Test
  @WithUserDetails("just-logged")
  fun `test should store user in database and return that in the http response when POST UserController@store`() {
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

    every(userRepository.save(any(User::class.java))).thenReturn(user)

    mockMvc.perform(post(STORE_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userRepository, times(1)).save(any(User::class.java))
  }

  @Test
  @WithUserDetails("just-logged")
  fun `test should update user in database that have the id 1 and return that in the http response when PUT UserController@update with id path variable 1`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password",
      roles = mutableSetOf()
    )

    val (id) = user

    val body = UserUpdateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every(userRepository.findById(id)).thenReturn(Optional.of(user))
    every(userRepository.save(any(User::class.java))).thenReturn(user)

    mockMvc.perform(put(UPDATE_ENDPOINT.replace("{id}", id.toString()))
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userRepository, times(1)).findById(id)
    verify(userRepository, times(1)).save(any(User::class.java))
  }

  @Test
  @WithUserDetails("just-logged")
  fun `test should delete user that have the id 1 and return no content http response when DELETE UserController@destroy with id path variable 1`() {
    val user = User(
      id = 0L,
      username = "fake username",
      email = "fake email",
      password = "fake password",
      roles = mutableSetOf()
    )

    val (id) = user

    every(userRepository.deleteById(id)).then {
      // do nothing.
    }

    mockMvc.perform(delete(DESTROY_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isNoContent)

    verify(userRepository, times(1)).deleteById(id)
  }

}
