package com.lorenzoog.gitkib.userservice.bodies

data class UserUpdateBody(
  val username: String?,
  val email: String?,
  val password: String?
)
