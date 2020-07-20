package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE

object UserRoleTable : LongIdTable(name = "user_role") {

  val user = long("user_id").entityId().references(ref = UserTable.id, onDelete = CASCADE)
  val role = long("role_id").entityId().references(ref = RoleTable.id, onDelete = CASCADE)

}
