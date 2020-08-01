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

  override fun connect() = Database.connect(hikariDataSource)

  override fun createSchemas(database: Database) = transaction(database) {
    SchemaUtils.create(*tables)
  }
}
