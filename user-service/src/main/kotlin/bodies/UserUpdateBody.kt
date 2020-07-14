package com.lorenzoog.gitkib.userservice.bodies

import javax.persistence.Column
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Table(
  name = "users",
  uniqueConstraints = [
    UniqueConstraint(columnNames = ["username", "email"])
  ]
)
data class UserUpdateBody(
  @Size(min = 4, max = 32)
  @Column(unique = true)
  val username: String?,

  @Size(min = 4, max = 32)
  @Email
  @Column(unique = true)
  val email: String?,

  @Size(min = 8, max = 24)
  val password: String?
)
