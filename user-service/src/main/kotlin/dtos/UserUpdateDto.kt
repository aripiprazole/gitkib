package com.lorenzoog.gitkib.userservice.dtos

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint

class UserUpdateDto(
  val username: String?,
  val email: String?,
  val password: String?
) {
  val validator = ValidatorBuilder.of<UserUpdateDto>()
    .konstraint(UserUpdateDto::username) {
      notBlank()
        .greaterThan(4)
        .lessThan(32)
    }
    .konstraint(UserUpdateDto::email) {
      notBlank()
        .greaterThan(4)
        .lessThan(32)
    }
    .konstraint(UserUpdateDto::password) {
      notBlank()
        .greaterThan(8)
        .lessThan(16)
    }
    .build()
}
