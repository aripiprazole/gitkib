package com.lorenzoog.gitkib.userservice.services.impl

import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.Application
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.Database

class ExposedDatabaseService : DatabaseService {
  @KtorExperimentalAPI
  private fun dataSource(config: ApplicationConfig) = HikariDataSource(HikariConfig().apply {
    jdbcUrl = config.property("url").getString()
    username = config.property("user").getString()
    password = config.property("password").getString()

    maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2
  })

  @KtorExperimentalAPI
  override fun Application.connect() {
    Database.connect(dataSource(environment.config.config("database")))
  }

  override suspend fun createSchemas() {
    TODO("Not yet implemented")
  }

}
