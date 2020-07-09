package com.lorenzoog.gitkib.commons.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable(name = "users") {

  val username = varchar("name", 32)
  val email = varchar("email", 32).uniqueIndex()
  val password = varchar("password", 72)

}
