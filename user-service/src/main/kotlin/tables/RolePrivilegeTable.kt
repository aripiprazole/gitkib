package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import org.jetbrains.exposed.sql.Table

object RolePrivilegeTable : Table(name = "role_privilege") {

  val role = reference(name = "role", foreign = RoleTable, onDelete = CASCADE)
  val privilege = reference(name = "privilege", foreign = PrivilegeTable, onDelete = CASCADE)

}
