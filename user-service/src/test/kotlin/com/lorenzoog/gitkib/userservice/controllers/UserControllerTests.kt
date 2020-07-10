package com.lorenzoog.gitkib.userservice.controllers

import com.github.javafaker.Faker
import com.google.gson.GsonBuilder
import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.repositories.Repository
import com.lorenzoog.gitkib.commons.database.tables.UserTable
import com.lorenzoog.gitkib.userservice.setup
import io.ktor.application.Application
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.routing.routing
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import junit.framework.TestCase
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import java.text.DateFormat
import java.util.Locale.ENGLISH

import org.mockito.Mockito.`when` as whenExecute

@KtorExperimentalAPI
class UserControllerTests : TestCase() {

  private val database =
    Database.connect(
      url = "jdbc:h2:mem:local;MODE=MySQL;DATABASE_TO_UPPER=false;",
      driver = "org.h2.Driver",
      user = "root",
      password = "root"
    )
  private val userRepositoryMock = mock(Repository::class.java)
  private val objectMapper = GsonBuilder().create()
  private val faker = Faker(ENGLISH)
  private val applicationMock: Application.() -> Unit = {
    setup()

    routing {
      @Suppress("UNCHECKED_CAST")
      userController(database, userRepositoryMock as Repository<Long, User>)
    }
  }

  fun `test should show paginated users when get 'users' route`() {
    val usersMock = transaction(database) {
      SchemaUtils.create(UserTable)

      listOf(
        (0..faker.number().numberBetween(10, 30)).map {
          User.new {
            username = faker.lorem().characters(32)
            email = faker.lorem().characters(32)
            password = faker.lorem().characters(32)
          }
        }
      )
    }

    whenExecute(
      userRepositoryMock.paginate(
        page = 0,
        offset = PAGINATION_OFFSET
      )
    ).thenReturn(usersMock)

    withTestApplication(applicationMock) {
      with(handleRequest(Get, "users")) {
        assertNotNull(response.content)
        assertEquals(OK, response.status())
        assertEquals(objectMapper.toJson(usersMock), response.content)
      }

      stop(0, 0)
    }
  }


}
