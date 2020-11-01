package com.lorenzoog.gitkib.userservice

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.mainRouter() {
  get("/") {
    call.respond(mapOf(
      "message" to "Hello, world"
    ))
  }
}
