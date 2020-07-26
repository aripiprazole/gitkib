package com.lorenzoog.gitkib.userservice.services.impl

import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class PostgreSqlDatabaseService(private val tables: Array<Table>) : DatabaseService {
  private val hikariConfig by lazy {
    HikariConfig()
  }
  private val hikariDataSource: DataSource by lazy {
    HikariDataSource(hikariConfig)
  }
  private val database: Database by lazy {
    Database.connect(hikariDataSource)
  }

  override fun connect() = database

  override fun createSchemas() = transaction {
    SchemaUtils.create(*tables)
  }
}
