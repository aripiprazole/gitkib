package com.lorenzoog.gitkib.userservice.tests.services

import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.lorenzoog.gitkib.userservice.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class H2DatabaseService : DatabaseService {
  private val database by lazy {
    Database.connect(
      url = "jdbc:h2:mem:local;MODE=POSTGRESQL;DATABASE_TO_UPPER=FALSE",
      driver = "org.h2.Driver"
    )
  }

  override fun connect() = database

  override fun createSchemas() = transaction(database) {
    SchemaUtils.create(Users)
  }
}
