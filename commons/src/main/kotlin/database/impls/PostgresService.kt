package com.lorenzoog.gitkib.commons.database.impls

import com.lorenzoog.gitkib.commons.database.DatabaseService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

class PostgresService : DatabaseService {

  override fun connect(environment: Dotenv): Database {
    val config = HikariConfig().apply {
      jdbcUrl = environment["DB_URL"] ?: throw IllegalStateException("The database jdbc url can not be null")
      username = environment["DB_USERNAME"] ?: throw IllegalStateException("The database username can not be null")
      password = environment["DB_PASSWORD"] ?: throw IllegalStateException("The database password can not be null")
    }

    val dataSource: DataSource = HikariDataSource(config)

    return Database.connect(dataSource)
  }

}
