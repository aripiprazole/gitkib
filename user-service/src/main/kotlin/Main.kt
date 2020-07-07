package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.controllers.userController
import com.lorenzoog.gitkib.userservice.database.DatabaseService
import com.lorenzoog.gitkib.userservice.database.impls.PostgresService
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, commandLineEnvironment(args))
    val environment = dotenv()

    // The service that will connect to the database
    val databaseService: DatabaseService = PostgresService()
    val database = databaseService.connect(environment)

    // Setup application controllers/routes
    server.application.apply {
        userController(database)
    }

    server.start()
}