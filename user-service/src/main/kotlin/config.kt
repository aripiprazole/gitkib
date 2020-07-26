package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.lorenzoog.gitkib.userservice.services.UserService
import com.lorenzoog.gitkib.userservice.services.impl.PostgreSqlDatabaseService
import com.lorenzoog.gitkib.userservice.tables.Users
import org.jetbrains.exposed.sql.Table
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.web.reactive.function.server.RouterFunctions

val tables: Array<Table> = arrayOf(
  Users
)

fun BeanDefinitionDsl.setupDefaultBeans() {
  bean<UserService>()

  bean<DatabaseService> {
    PostgreSqlDatabaseService(tables)
  }

  bean("webHandler") {
    RouterFunctions.toWebHandler(routes(ref()))
  }
}
