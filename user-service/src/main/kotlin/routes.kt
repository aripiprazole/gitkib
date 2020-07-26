package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.controllers.userController
import com.lorenzoog.gitkib.userservice.services.UserService
import org.springframework.web.reactive.function.server.coRouter

fun routes(userService: UserService) = coRouter {
  userController(userService)
}
