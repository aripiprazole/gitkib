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
import kotlinx.coroutines.runBlocking
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

import org.mockito.Mockito.`when` as every

private val objectMapper = ObjectMapper()

@SpringBootTest(
  classes = [SecurityTestConfig::class]
)
@AutoConfigureMockMvc
// TODO: use a faker library
class UserControllerTests {

  @MockBean
  private lateinit var userProvider: UserProvider

  @Autowired
  private lateinit var mockMvc: MockMvc

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should show users paginated when GET UserController@index`() = runBlocking {
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

    every(userProvider.findAll(page = 0, offset = USER_PAGINATION_OFFSET)).thenReturn(page)

    mockMvc.perform(get(INDEX_ENDPOINT).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(userProvider, times(1)).findAll(page = 0, offset = USER_PAGINATION_OFFSET)

    Unit
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should show user that have id 1 when GET UserController@show with id path variable 1`() = runBlocking {
    val user = User.mock()
    val id = user.id.value

    every(userProvider.findById(id)).thenReturn(user)

    mockMvc.perform(get(SHOW_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userProvider, times(1)).findById(id)

    Unit
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should store user in database and return that in the http response when POST UserController@store`() = runBlocking {
    val user = User.mock()

    val body = UserCreateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every(userProvider.save(any())).thenReturn(user)

    mockMvc.perform(post(STORE_ENDPOINT)
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userProvider, times(1)).save(any())

    Unit
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should update user in database that have the id 1 and return that in the http response when PUT UserController@update with id path variable 1`() = runBlocking {
    val user = User.mock()
    val id = user.id.value

    val body = UserUpdateBody(
      username = user.username,
      email = user.email,
      password = user.password
    )

    every(userProvider.findById(id)).thenReturn(user)
    every(user.update(any(), any())).thenReturn(user)

    mockMvc.perform(put(UPDATE_ENDPOINT.replace("{id}", id.toString()))
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body)))

      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(user)))

    verify(userProvider, times(1)).findById(id)
    verify(user, times(1)).update(any(), any())

    Unit
  }

  @Test
  @WithUserDetails(DefaultUsers.ALL_PERMISSIONS)
  fun `test should delete user that have the id 1 and return no content http response when DELETE UserController@destroy with id path variable 1`() = runBlocking {
    val user = User.mock()
    val id = user.id.value

    every(userProvider.deleteById(id)).then {
      // do nothing.
    }

    mockMvc.perform(delete(DESTROY_ENDPOINT.replace("{id}", id.toString())).contentType(APPLICATION_JSON))
      .andExpect(status().isNoContent)

    verify(userProvider, times(1)).deleteById(id)

    Unit
  }

}
