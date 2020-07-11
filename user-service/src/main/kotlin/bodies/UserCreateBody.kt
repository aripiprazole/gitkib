package com.lorenzoog.gitkib.userservice.bodies

import com.lorenzoog.gitkib.userservice.validators.UniqueColumn
import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class UserCreateBody(
  @Size(min = 4, max = 32)
  @UniqueColumn(column = "email", message = "There is already exists an user with this username.")
  val username: String,

  @Size(min = 4, max = 32)
  @Email
  @UniqueColumn(column = "email", message = "There is already exists an user with this email.")
  val email: String,

  @Size(min = 8, max = 24)
  val password: String
)
