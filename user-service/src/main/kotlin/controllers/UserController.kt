package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.repositories.Repository
import com.lorenzoog.gitkib.userservice.validators.UserUpdateValidator
import com.lorenzoog.gitkib.userservice.validators.UserCreateValidator
import io.ktor.application.call
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

const val PAGINATION_OFFSET = 15

@KtorExperimentalAPI
fun Route.userController(database: Database, userRepository: Repository<Long, User>) {
  get("users") {
    val page = call.request.queryParameters["page"] ?: "0"

    call.respond(userRepository.paginate(page.toInt(), PAGINATION_OFFSET))
  }

  get("users/{id}") {
    val userId = call.parameters["id"]!!
    val user = userRepository.findById(userId.toLong())

    call.respond(user)
  }

  post("users") {
    val body = call.receive<UserCreateValidator>().also(UserCreateValidator::validate)

    val user = userRepository.create {
      username = body.username
      email = body.email
      password = body.password
    }

    call.respond(Created, user)
  }

  put("users/{id}") {
    val userId = call.parameters["id"]!!
    val body = call.receive<UserUpdateValidator>().also(UserUpdateValidator::validate)

    transaction(database) {
      val user = userRepository.findById(userId.toLong())

      user.apply {
        body.username?.let { username = it }
        body.email?.let { email = it }
        body.password?.let { password = it }
      }

      user.refresh()
    }

    call.respond(NoContent)
  }

  delete("users/{id}") {
    val userId = call.parameters["id"]!!

    userRepository.deleteById(userId.toLong())

    call.respond(NoContent)
  }
}
