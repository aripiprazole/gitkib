package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.utils.paginate
import com.lorenzoog.gitkib.commons.utils.whenNotNull
import com.lorenzoog.gitkib.userservice.validators.UserUpdateValidator
import com.lorenzoog.gitkib.userservice.validators.UserCreateValidator
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

const val PAGINATION_OFFSET = 15

@KtorExperimentalAPI
fun Route.userController(database: Database) {
  get("users") {
    val page = call.request.queryParameters["page"] ?: "0"

    newSuspendedTransaction(db = database) {
      val users = User.all().paginate(page.toInt(), PAGINATION_OFFSET)

      call.respond(users)
    }
  }

  get("users/{id}") {
    val userId = call.parameters["id"]!!

    newSuspendedTransaction(db = database) {
      val user = User.findById(userId.toLong()) ?: throw NotFoundException()

      call.respond(user)
    }
  }

  post("users") {
    val body = call.receive<UserCreateValidator>().also(UserCreateValidator::validate)

    newSuspendedTransaction(db = database) {
      val user = User.new {
        username = body.username
        email = body.email
        password = body.password
      }

      call.respond(Created, user)
    }
  }

  put("users/{id}") {
    val userId = call.parameters["id"]!!
    val body = call.receive<UserUpdateValidator>()

    newSuspendedTransaction(db = database) {
      val user = User.findById(userId.toLong()) ?: throw NotFoundException()

      body.username.whenNotNull { user.username = it }
      body.email.whenNotNull { user.email = it }
      body.password.whenNotNull { user.password = it }

      user.refresh()

      call.respond(NoContent)
    }
  }

  delete("users/{id}") {
    // TODO
  }
}
