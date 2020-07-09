package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.utils.paginate
import io.ktor.application.call
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
    // TODO
  }

  post("users") {
    // TODO
  }

  put("users/{id}") {
    // TODO
  }

  delete("users/{id}") {
    // TODO
  }
}
