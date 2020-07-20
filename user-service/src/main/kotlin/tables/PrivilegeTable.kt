package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object PrivilegeTable : LongIdTable(name = "privileges") {

  val name = varchar(name = "name", length = 32)

}
