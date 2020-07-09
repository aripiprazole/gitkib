package com.lorenzoog.gitkib.userservice.validators

import com.lorenzoog.gitkib.commons.utils.Validator
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class UserUpdateValidator (
  @field:Min(4)
  @field:Max(32)
  val username: String?,

  @field:Min(6)
  @field:Max(32)
  val email: String?,

  @field:Min(8)
  @field:Max(24)
  val password: String?
): Validator()
