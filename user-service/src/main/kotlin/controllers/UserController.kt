package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.tables.UserTable
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.select

const val PAGINATION_OFFSET = 15

fun Application.userController(database: Database) {
  routing {
    get("users") {
      val users = User.all().limit(PAGINATION_OFFSET)

      call.respond(HttpStatusCode.OK, users)
    }
  }
}
