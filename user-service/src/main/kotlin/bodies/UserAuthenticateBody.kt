package com.lorenzoog.gitkib.userservice.bodies

import com.lorenzoog.gitkib.userservice.validators.UniqueColumn
import javax.validation.constraints.Size

data class UserAuthenticateBody(
  @Size(min = 4, max = 32)
  @UniqueColumn(column = "username", message = "There is already exists an user with this username.")
  val username: String,

  @Size(min = 8, max = 24)
  val password: String
)
