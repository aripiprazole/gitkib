package com.lorenzoog.gitkib.userservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
  val id: Long,
  val username: String,
  val email: String
)
