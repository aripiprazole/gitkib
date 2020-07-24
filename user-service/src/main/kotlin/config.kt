package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.controllers.userController
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.coRouter

val defaultBeans = beans {
  bean("webHandler") {
    RouterFunctions.toWebHandler(
      coRouter {
        userController(ref())
      }
    )
  }
}
