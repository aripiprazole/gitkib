package com.lorenzoog.gitkib.userservice

import io.ktor.application.Application
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun main(args: Array<String>) {
  val server = embeddedServer(Netty, commandLineEnvironment(args))

  server.start()

  // Setup application controllers/routes and other services
  server.application
    .apply(Application::setup)
    .apply(Application::controllers)
}
