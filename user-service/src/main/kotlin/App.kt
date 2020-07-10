package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.commons.database.DatabaseService
import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.impls.PostgresService
import com.lorenzoog.gitkib.commons.database.repositories.Repository
import com.lorenzoog.gitkib.commons.database.repositories.UserRepository
import com.lorenzoog.gitkib.userservice.controllers.userController
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.util.KtorExperimentalAPI

val env = dotenv()

fun Application.setup() {
  install(ContentNegotiation) {
    json()
  }
}

@KtorExperimentalAPI
fun Application.controllers() {
  // The service that will connect to the database
  val databaseService: DatabaseService = PostgresService()
  val database = databaseService.connect(env)

  val userRepository: Repository<Long, User> = UserRepository(database)

  routing {
    userController(database, userRepository)
  }
}
