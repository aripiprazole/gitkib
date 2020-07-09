package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.utils.paginate
import com.lorenzoog.gitkib.userservice.validators.UserValidator
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.http.HttpStatusCode.Companion.Created
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
    val userValidator = call.receive<UserValidator>().also(UserValidator::validate)

    newSuspendedTransaction(db = database) {
      val user = User.new {
        username = userValidator.username
        email = userValidator.email
        password = userValidator.password
      }

      call.respond(Created, user)
    }
  }

  put("users/{id}") {
    // TODO
  }

  delete("users/{id}") {
    // TODO
  }
}
