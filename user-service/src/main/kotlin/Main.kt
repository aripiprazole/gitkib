package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.commons.database.DatabaseService
import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.impls.PostgresService
import com.lorenzoog.gitkib.commons.database.repositories.Repository
import com.lorenzoog.gitkib.commons.database.repositories.UserRepository
import com.lorenzoog.gitkib.userservice.controllers.userController
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
fun main(args: Array<String>) {
  val server = embeddedServer(Netty, commandLineEnvironment(args))
  val environment = dotenv()

  // The service that will connect to the database
  val databaseService: DatabaseService = PostgresService()
  val database = databaseService.connect(environment)

  val userRepository: Repository<Long, User> = UserRepository(database)

  server.start()

  // Setup application controllers/routes and other services
  server.application.apply {
    install(ContentNegotiation) {
      gson()
    }

    routing {
      userController(database, userRepository)
    }
  }
}
