package com.lorenzoog.gitkib.commons.database.impls

import com.lorenzoog.gitkib.commons.database.DatabaseService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

class PostgresService : DatabaseService {

  private fun errorNonexistentEnvVariable(variable: String): Nothing =
    throw IllegalStateException("The database $variable can not be null")

  override fun connect(environment: Dotenv): Database {
    val config = HikariConfig().apply {
      jdbcUrl = environment["DB_URL"] ?: errorNonexistentEnvVariable("jdbc url")
      username = environment["DB_USERNAME"] ?: errorNonexistentEnvVariable("username")
      password = environment["DB_PASSWORD"] ?: errorNonexistentEnvVariable("password")
    }

    val dataSource: DataSource = HikariDataSource(config)

    return Database.connect(dataSource)
  }

}
