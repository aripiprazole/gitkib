package me.devgabi.gitkib.userservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCreateDto(
  val username: String,
  val email: String,
  val password: String
)
