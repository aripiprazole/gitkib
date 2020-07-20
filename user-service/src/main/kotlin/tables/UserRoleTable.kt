package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE

object UserRoleTable : LongIdTable(name = "user_role") {

  val user = reference(name = "user", foreign = UserTable, onDelete = CASCADE)
  val role = reference(name = "role", foreign = RoleTable, onDelete = CASCADE)

}
