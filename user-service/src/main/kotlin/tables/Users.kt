package me.devgabi.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable() {
  val username = varchar("username", 32)
  val email = varchar("email", 24)
  val password = text("password")
}
