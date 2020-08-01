package com.lorenzoog.gitkib.userservice.utils

import am.ik.yavi.core.Validator
import com.lorenzoog.gitkib.userservice.exceptions.ValidationException

fun <T> Validator<T>.validateAndThrow(target: T) {
  val constraints = validate(target)

  if(constraints.isValid) return

  throw ValidationException(constraints.violations())
}
