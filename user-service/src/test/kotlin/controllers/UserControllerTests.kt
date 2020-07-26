package com.lorenzoog.gitkib.userservice.tests.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lorenzoog.gitkib.userservice.controllers.USER_CONTROLLER_PAGE_SIZE
import com.lorenzoog.gitkib.userservice.dtos.Page
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserService
import com.lorenzoog.gitkib.userservice.tests.connectToDatabase
import com.lorenzoog.gitkib.userservice.tests.createApplication
import com.lorenzoog.gitkib.userservice.tests.factories.Factory
import com.lorenzoog.gitkib.userservice.tests.factories.UserFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body

class UserControllerTests : TestCase() {

  private val userService = mockk<UserService>()
  private val application = createApplication {
    bean(isPrimary = true) {
      userService
    }
  }
  private val client = WebTestClient
    .bindToServer()
    .baseUrl(application.url)
    .build()
  private val factory: Factory<User> = UserFactory()
  private val json = jacksonObjectMapper()

  override fun setUp() {
    connectToDatabase()

    application.start()
  }

  fun `test should get paginated users when request users`() = runBlocking<Unit> {
    val users = factory.createMany(USER_CONTROLLER_PAGE_SIZE)

    coEvery { userService.findAll(1, USER_CONTROLLER_PAGE_SIZE) } returns Page(
      content = users.toList(),
      total = USER_CONTROLLER_PAGE_SIZE,
      currentPage = 1
    )

    client.get()
      .uri("/")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .json(json.writeValueAsString(mapOf(
        "content" to users,
        "total" to USER_CONTROLLER_PAGE_SIZE,
        "currentPage" to 1
      )))
  }

  fun `test should get one user when request users 1`() = runBlocking<Unit> {
    val user = factory.createOne()

    coEvery { userService.findById(user.id.value) } returns user

    client.get()
      .uri("/${user.id}")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .json(json.writeValueAsString(mapOf(
        "id" to user.id.value,
        "username" to user.username,
        "email" to user.email
      )))
  }

  fun `test should store one user in the database when request users`() = runBlocking<Unit> {
    val username = "fake username"
    val email = "fake email"
    val password = "fake password"

    val user = factory.createOne {
      this.username = username
      this.email = email
      this.password = password
    }

    coEvery { userService.save(any()) } returns user

    client.post()
      .uri("/")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(flowOf(json.writeValueAsString(mapOf(
        "username" to username,
        "email" to email,
        "password" to password
      ))))
      .exchange()
      .expectStatus()
      .isCreated
      .expectBody()
      .json(json.writeValueAsString(mapOf(
        "id" to user.id.value,
        "username" to username,
        "email" to email
      )))
  }

  fun `test should update one user in the database when request users 1`() = runBlocking<Unit> {
    val user = factory.createOne()

    val username = "fake username"
    val email = "fake email"
    val password = "fake password"

    coEvery { userService.updateById(user.id.value, any()) } returns transaction {
      user.apply {
        this.username = username
        this.email = email
        this.password = password
      }
    }

    client.put()
      .uri("/${user.id}")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(flowOf(json.writeValueAsString(mapOf(
        "username" to username,
        "email" to email,
        "password" to password
      ))))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .json(json.writeValueAsString(mapOf(
        "id" to user.id.value,
        "username" to username,
        "email" to email
      )))
  }

  fun `test should delete user in the database when request users 1`() = runBlocking<Unit> {
    val user = factory.createOne()

    coEvery { userService.deleteById(user.id.value) } returns Unit

    client.delete()
      .uri("/${user.id}")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isNoContent
  }

  override fun tearDown() {
    application.stop()
  }

}
