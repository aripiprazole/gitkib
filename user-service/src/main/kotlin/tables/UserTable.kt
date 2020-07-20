package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UserTable : LongIdTable(name = "users") {

  val username = varchar(name = "username", length = 32)
  val email = varchar(name = "email", length = 32)
  val password = varchar(name = "password", length = 24)

}
