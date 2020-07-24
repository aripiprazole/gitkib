package com.lorenzoog.gitkib.userservice.exceptions

import java.lang.RuntimeException

class EntityNotFoundException(message: String = "Entity not found!", throwable: Throwable? = null) :
  RuntimeException(message, throwable)

