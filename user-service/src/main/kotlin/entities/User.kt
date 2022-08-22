package me.devgabi.gitkib.userservice.entities

import me.devgabi.gitkib.userservice.dto.UserResponseDto
import me.devgabi.gitkib.userservice.tables.Users
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {

  var username by Users.username
  var email by Users.email
  var password by Users.password

  fun toDto() = UserResponseDto(
    id = id.value,
    username = username,
    email = email
  )

  companion object : LongEntityClass<User>(Users)

}
