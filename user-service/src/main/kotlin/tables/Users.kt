package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable("users") {
  val username = varchar("username", 32)
  val email = varchar("email", 32)
  val password = varchar("password", 32)
}
