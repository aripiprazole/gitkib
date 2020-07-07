package com.lorenzoog.gitkib.userservice.database.impls

import com.lorenzoog.gitkib.userservice.database.DatabaseService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database
import java.io.PrintWriter
import javax.sql.DataSource

class PostgresService : DatabaseService {

    override fun connect(environment: Dotenv): Database {
        val config = HikariConfig().apply {
            jdbcUrl = environment["DB_URL"] ?: throw IllegalStateException("The database jdbc url can not be null")
            username = environment["DB_USERNAME"] ?: throw IllegalStateException("The database username can not be null")
            password = environment["DB_PASSWORD"] ?: throw IllegalStateException("The database password can not be null")

            // TODO: create an specific log for data source
            dataSource.logWriter = PrintWriter(System.out)
        }

        val dataSource: DataSource = HikariDataSource(config)

        return Database.connect(dataSource)
    }

}