package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.Table

object RolePrivilegeTable : Table(name = "user_profile") {

  val role = long("role_id").entityId().references(ref = RoleTable.id, onDelete = CASCADE)
  val privilege = long("privilege_id").entityId().references(ref = PrivilegeTable.id, onDelete = CASCADE)

}
