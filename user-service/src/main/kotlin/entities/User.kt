package com.lorenzoog.gitkib.userservice.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import com.lorenzoog.gitkib.userservice.tables.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {
  var username: String by UserTable.username

  var email: String by UserTable.email

  @get:JsonProperty(access = READ_ONLY)
  @get:JsonIgnore
  var password: String by UserTable.password

  val roles: MutableSet<Role> = mutableSetOf()

  companion object : LongEntityClass<User>(UserTable)
}
