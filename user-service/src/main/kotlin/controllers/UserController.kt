package com.lorenzoog.gitkib.userservice.controllers

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database

fun Application.userController(database: Database) {
  routing {

  }
}
