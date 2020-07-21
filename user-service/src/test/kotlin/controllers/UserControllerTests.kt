package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.config.DefaultUsers
import com.lorenzoog.gitkib.userservice.config.SecurityTestConfig
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.DESTROY_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.INDEX_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.SHOW_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.STORE_ENDPOINT
import com.lorenzoog.gitkib.userservice.controllers.UserController.Companion.UPDATE_ENDPOINT
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserProvider
import com.lorenzoog.gitkib.userservice.services.update
import com.lorenzoog.gitkib.userservice.utils.mock
import com.ninjasquad.springmockk.MockkBean
import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.springframework.security.test.context.support.WithUserDetails

private val objectMapper = ObjectMapper()

@SpringBootTest(
  classes = [SecurityTestConfig::class]
)
@AutoConfigureMockMvc
// TODO: use a faker library
class UserControllerTests {
  @MockkBean
  private lateinit var userProvider: UserProvider

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should show users paginated when GET UserController@index`() {
    val users = listOf(
      User.mock(),
      User.mock(),
      User.mock()
    )

    val page: Page<User> = PageImpl(
      users,
      Pageable.unpaged(),
      users.size.toLong()
    )

    every { userProvider.findAll(page = 0, offset = USER_PAGINATION_OFFSET) } returns page

    mockMvc.perform(get(INDEX_ENDPOINT).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(atMost = 1, atLeast = 1) {
      userProvider.findAll(page = 0, offset = USER_PAGINATION_OFFSET)
    }
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should show user that have id 1 when GET UserController@show with id path variable 1`() {
    val user = User.mock()
    val id = user.id.value

    every { userProvider.findById(id) } returns user

    mockMvc.perform(get(SHOW_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(atMost = 1, atLeast = 1) {
      userProvider.findById(id)
    }
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should store user in database and return that in the http response when POST UserController@store`() {
    val user = User.mock()

    val body = UserCreateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every { userProvider.save(allAny()) } returns user

    mockMvc.perform(post(STORE_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(atMost = 1, atLeast = 1) {
      userProvider.save(allAny())
    }
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should update user in database that have the id 1 and return that in the http response when PUT UserController@update with id path variable 1`() {
    val user = User.mock()
    val id = user.id.value

    val body = UserUpdateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every { userProvider.findById(id) } returns user
    every { user.update(any(), any()) } returns user

    mockMvc.perform(put(UPDATE_ENDPOINT.replace("{id}", id.toString()))
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(atMost = 1, atLeast = 1) {
      userProvider.findById(1)
    }

    verify(atMost = 1, atLeast = 1) {
      user.update(any(), any())
    }
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should delete user that have the id 1 and return no content http response when DELETE UserController@destroy with id path variable 1`() {
    val user = User.mock()
    val id = user.id.value

    every { userProvider.deleteById(id) } answers {
      // nothing.
    }

    mockMvc.perform(delete(DESTROY_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isNoContent)

    verify(atMost = 1, atLeast = 1) {
      userProvider.deleteById(id)
    }
  }

}
