package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.services.UserService
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions

val defaultBeans = beans {
  bean<UserService>()

  bean("webHandler") {
    RouterFunctions.toWebHandler(routes(ref()))
  }
}
