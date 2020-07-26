package com.lorenzoog.gitkib.userservice.tests

import com.lorenzoog.gitkib.userservice.Application
import com.lorenzoog.gitkib.userservice.routes
import com.lorenzoog.gitkib.userservice.services.UserService
import org.jetbrains.exposed.sql.Database
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions

@Suppress("NOTHING_TO_INLINE")
inline fun connectToDatabase() = Database.connect(
  url = "jdbc:h2:mem:local;MODE=POSTGRESQL;DATABASE_TO_UPPER=FALSE",
  driver = "org.h2.Driver"
)

@Suppress("NOTHING_TO_INLINE")
inline fun createApplication() = Application(beanDefinitions = beans {
  bean<UserService>()

  bean("webHandler") {
    RouterFunctions.toWebHandler(routes(ref()))
  }
})
