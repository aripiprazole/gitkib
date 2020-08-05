package com.lorenzoog.gitkib.userservice.tests.routes

import com.lorenzoog.gitkib.userservice.dto.Page
import com.lorenzoog.gitkib.userservice.dto.UserCreateDto
import com.lorenzoog.gitkib.userservice.dto.UserResponseDto
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserService
import com.lorenzoog.gitkib.userservice.tests.createApplication
import com.lorenzoog.gitkib.userservice.tests.factories.UserFactory
import com.lorenzoog.gitkib.userservice.tests.utils.actingAs
import com.lorenzoog.gitkib.userservice.tests.utils.request
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.properties.Delegates.notNull

@KtorExperimentalAPI
class UserRoutesTests : Spek({
  val application = createApplication {
    put("database.url", "jdbc:h2:mem:local;MODE=POSTGRESQL;DATABASE_TO_UPPER=FALSE")
    put("database.driver", "org.h2.Driver")
    put("database.user", "root")
    put("database.password", "")
  }

  val di = di { application.application }

  beforeEachTest { application.start(true) }
  afterEachTest { application.stop(1000, 1000) }

  Feature("user-rest-api") {
    val userService by di.instance<UserService>()
    val userFactory = UserFactory()

    val client = HttpClient(CIO)
    val json = Json(JsonConfiguration.Stable)

    Scenario("list all users") {
      var user: User by notNull()
      var response: HttpResponse by notNull()

      Given("user with permission: users.view") {
        user = userFactory.createWithPermissions(listOf("users.view"))
      }

      When("request with GET to endpoint /users") {
        response = runBlocking {
          client.actingAs(user, di)
            .request<HttpStatement>(HttpMethod.Get, "/users")
            .execute()
        }
      }

      Then("it should show a response with status: 200") {
        assertThat(response.status, equalTo(HttpStatusCode.OK))
      }

      And("the content should be: a page with the users") {
        runBlocking {
          val expected = userService.findPaginated(0).map(User::toDto)

          response.content.read {
            assertThat(
              json.parse(
                Page.serializer(UserResponseDto.serializer()),
                Charsets.UTF_8.decode(it).toString()
              ),
              equalTo(expected)
            )
          }
        }
      }
    }

    Scenario("show one user") {
      var user: User by notNull()
      var response: HttpResponse by notNull()

      Given("user with permission: users.view") {
        user = userFactory.createWithPermissions(listOf("users.view"))
      }

      When("request with GET to endpoint: /users/${user.id.value}") {
        response = runBlocking {
          client.actingAs(user, di)
            .request<HttpStatement>(HttpMethod.Get, "/users/${user.id}")
            .execute()
        }
      }

      Then("it should show a response with status: 200") {
        assertThat(response.status, equalTo(HttpStatusCode.OK))
      }

      And("the content should be: the user with user response dto serializer") {
        runBlocking {
          val expected = userService.findById(user.id.value)

          response.content.read {
            assertThat(
              json.parse(
                UserResponseDto.serializer(),
                Charsets.UTF_8.decode(it).toString()
              ),
              equalTo(expected.toDto())
            )
          }
        }
      }
    }

    Scenario("create a new user") {
      var response: HttpResponse by notNull()

      Given("a unauthenticated or authenticated requester") {
        // nothing to do
      }

      When("request with POST to endpoint: /users") {
        response = runBlocking {
          client
            .request<HttpStatement>(HttpMethod.Post, "/users") {
              body = json.toJson(UserCreateDto.serializer(), UserCreateDto(
                username = "some username",
                email = "some email",
                password = "some password"
              ))
            }
            .execute()
        }
      }

      Then("it should show a response with status: 201") {
        assertThat(response.status, equalTo(HttpStatusCode.Created))
      }

      And("the content should be: the created user with user response dto serializer") {
        runBlocking {
          response.content.read {
            val userResponse = json.parse(
              UserResponseDto.serializer(),
              Charsets.UTF_8.decode(it).toString()
            )
            val expected = runBlocking { userService.findById(userResponse.id) }

            assertThat(userResponse, equalTo(expected.toDto()))
          }
        }
      }
    }
  }
})
