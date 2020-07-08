package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable(name = "users") {

  val username = varchar("name", 32)
  val email = varchar("name", 32)
  val password = varchar("password", 72)

}
