package com.lorenzoog.gitkib.userservice.exceptions

import am.ik.yavi.core.ConstraintViolation

class ValidationException(val violations: List<ConstraintViolation>) :
  Exception("There are validation errors!")
