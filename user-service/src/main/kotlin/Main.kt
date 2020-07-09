package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.commons.database.DatabaseService
import com.lorenzoog.gitkib.commons.database.impls.PostgresService
import com.lorenzoog.gitkib.commons.database.tables.UserTable
import com.lorenzoog.gitkib.userservice.controllers.userController
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.DateFormat

fun main(args: Array<String>) {
  val server = embeddedServer(Netty, commandLineEnvironment(args))
  val environment = dotenv()

  // The service that will connect to the database
  val databaseService: DatabaseService = PostgresService()
  val database = databaseService.connect(environment)

  // Create database.entities' database.tables
  transaction(database) {
    SchemaUtils.create(
      UserTable
    )
  }

  server.start()

  // Setup application controllers/routes and other services
  server.application.apply {
    install(ContentNegotiation) {
      jackson {
        dateFormat = DateFormat.getDateInstance()
      }
    }

    routing {
      userController(database)
    }
  }
}
