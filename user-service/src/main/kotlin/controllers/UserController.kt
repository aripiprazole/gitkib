package com.lorenzoog.gitkib.userservice.controllers

import io.ktor.routing.*
import org.jetbrains.exposed.sql.*

const val PAGINATION_OFFSET = 15

fun Route.userController(database: Database) {
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
