package com.lorenzoog.gitkib.userservice.tests.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.tables.Users
import com.lorenzoog.gitkib.userservice.tests.connectToDatabase
import com.lorenzoog.gitkib.userservice.tests.createApplication
import com.lorenzoog.gitkib.userservice.tests.factories.Factory
import com.lorenzoog.gitkib.userservice.tests.factories.UserFactory
import com.lorenzoog.gitkib.userservice.utils.findOne
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body

class UserControllerTests : TestCase() {

  private val application = createApplication()
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
    client.get()
      .uri("/")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .json(json.writeValueAsString(mapOf(
        "content" to factory.createMany(15),
        "total" to 2,
        "currentPage" to 1
      )))
  }

  fun `test should get one user when request users 1`() = runBlocking<Unit> {
    val user = factory.createOne()

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

    val user by lazy {
      transaction {
        User.findOne {
          (Users.username eq username)
            .and(Users.email eq email)
        }
      }
    }

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

    val updatedUser by lazy {
      transaction {
        User.findOne {
          (Users.username eq username)
            .and(Users.email eq email)
        }
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
        "id" to updatedUser.id.value,
        "username" to username,
        "email" to email
      )))
  }

  fun `test should delete user in the database when request users 1`() = runBlocking<Unit> {
    val user = factory.createOne()

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
