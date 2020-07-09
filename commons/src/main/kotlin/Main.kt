package com.lorenzoog.gitkib.commons

import com.lorenzoog.gitkib.commons.database.impls.PostgresService
import com.lorenzoog.gitkib.commons.database.tables.UserTable
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Run common actions for the project
 *
 * @return [Unit]
 */
fun main() {
  createTables()
}

/**
 * Create project tables
 *
 * @return [Unit]
 */
fun createTables() = transaction(
  PostgresService().connect(dotenv())
) {
  SchemaUtils.create(UserTable)
}
