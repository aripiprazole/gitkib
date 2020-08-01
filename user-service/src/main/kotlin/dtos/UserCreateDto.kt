package com.lorenzoog.gitkib.userservice.dtos

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint

class UserCreateDto(
  val username: String?,
  val email: String?,
  val password: String?
) {
  val validator = ValidatorBuilder.of<UserCreateDto>()
    .konstraint(UserCreateDto::username) {
      notNull()
        .notBlank()
        .greaterThan(4)
        .lessThan(32)
    }
    .konstraint(UserCreateDto::email) {
      notNull()
        .notBlank()
        .greaterThan(4)
        .lessThan(32)
    }
    .konstraint(UserCreateDto::password) {
      notNull()
        .notBlank()
        .greaterThan(8)
        .lessThan(16)
    }
    .build()
}
