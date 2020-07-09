package com.lorenzoog.gitkib.database.entities

import com.lorenzoog.gitkib.commons.database.tables.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {
  companion object : LongEntityClass<User>(UserTable)

  val username: String by UserTable.username
  val email: String by UserTable.email
  val password: String by UserTable.password

}
