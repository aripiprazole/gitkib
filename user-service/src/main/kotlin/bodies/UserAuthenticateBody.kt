package com.lorenzoog.gitkib.userservice.bodies

import javax.validation.constraints.Size

data class UserAuthenticateBody(
  @Size(min = 4, max = 32)
  val username: String,

  @Size(min = 8, max = 24)
  val password: String
)
