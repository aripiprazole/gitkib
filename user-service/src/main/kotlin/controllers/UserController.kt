package com.lorenzoog.gitkib.userservice.controllers

import io.ktor.application.Application
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*

const val PAGINATION_OFFSET = 15

fun Application.userController(database: Database) {
  routing {
    get("users") {
      // TODO
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
}
