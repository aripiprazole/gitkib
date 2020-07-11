package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import io.mockk.every
import io.mockk.verify
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

private val objectMapper = ObjectMapper()

private const val INDEX_URL = "/users"
private const val STORE_URL = "/users"
private const val SHOW_URL = "/users/%s"
private const val UPDATE_URL = "/users/%s"
private const val DESTROY_URL = "/users/%s"

@SpringBootTest
@AutoConfigureMockMvc
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

    every { userRepository.findAll(any<Pageable>()) } returns page

    mockMvc.perform(get(INDEX_URL).contentType(APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(content().json(objectMapper.writeValueAsString(page)))

    verify(atLeast = 1, atMost = 1) { userRepository.findAll(any<Pageable>()) }
  }

}
