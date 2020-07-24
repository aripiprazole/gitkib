package com.lorenzoog.gitkib.userservice.entities

import com.lorenzoog.gitkib.userservice.tables.Users
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {
  val username by Users.username
  val email by Users.email
  val password by Users.password

  companion object : LongEntityClass<User>(Users)
}
