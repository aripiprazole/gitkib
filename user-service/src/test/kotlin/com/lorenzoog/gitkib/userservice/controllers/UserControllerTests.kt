package com.lorenzoog.gitkib.userservice.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.repositories.Repository
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import junit.framework.TestCase
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedCollection
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import java.text.DateFormat

import org.mockito.Mockito.`when` as whenExecute

@KtorExperimentalAPI
class UserControllerTests : TestCase() {

  private val database = Database.connect("jdbc:h2:mem:test", "org.h2.Driver", "root", "")
  private val userRepositoryMock = mock(Repository::class.java)
  private val objectMapper = ObjectMapper()
  private val faker = Faker()
  private val applicationMock: Application.() -> Unit = {
    install(ContentNegotiation) {
      jackson {
        dateFormat = DateFormat.getDateInstance()
      }
    }

    routing {
      @Suppress("UNCHECKED_CAST")
      userController(database, userRepositoryMock as Repository<Long, User>)
    }
  }

  fun `test should show paginated users when get 'users' route`() {
    val usersMock = listOf(
      (0 until faker.number().numberBetween(10, 30)).map {
        User.new {
          username = faker.lorem().characters(32)
          email = faker.lorem().characters(32)
          password = faker.lorem().characters(32)
        }
      }
    )

    whenExecute(
      userRepositoryMock.paginate(
        page = 0,
        offset = PAGINATION_OFFSET
      )
    ).thenReturn(SizedCollection(usersMock))

    withTestApplication(applicationMock) {
      with(handleRequest(Get, "users")) {
        assertNotNull(response.content)
        assertEquals(OK, response.status())
        assertEquals(objectMapper.writeValueAsString(usersMock), response.content)
      }
    }
  }


}
