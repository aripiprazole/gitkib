package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.services.UserService
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.web.reactive.function.server.RouterFunctions

fun BeanDefinitionDsl.setupDefaultBeans() {
  bean<UserService>()

  bean("webHandler") {
    RouterFunctions.toWebHandler(routes(ref()))
  }
}
