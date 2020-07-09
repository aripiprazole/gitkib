package com.lorenzoog.gitkib.commons

import com.lorenzoog.gitkib.commons.database.tables.UserTable
import org.jetbrains.exposed.sql.SchemaUtils

/**
 * Run common actions for the project
 *
 * @return [Unit]
 */
fun main() {
  // Creating project tables
  SchemaUtils.create(UserTable)
}
