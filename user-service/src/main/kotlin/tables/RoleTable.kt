package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object RoleTable : LongIdTable(name = "roles") {

  val name = varchar(name = "name", length = 32)
  val roles = null

}
