package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.lorenzoog.gitkib.userservice.services.UserService
import com.lorenzoog.gitkib.userservice.services.impl.ExposedDatabaseService
import com.lorenzoog.gitkib.userservice.services.impl.ExposedUserService
import io.ktor.application.Application
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun kodeinModule(app: Application) = DI.Module("kodein.module") {
  bind<DatabaseService>(tag = "database-service") with singleton { ExposedDatabaseService() }
  bind<UserService>(tag = "user-service") with singleton { ExposedUserService() }
}
