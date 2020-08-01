@file:OptIn(KtorExperimentalLocationsAPI::class)
@file:Suppress("unused")

package com.lorenzoog.gitkib.userservice

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

fun Application.appModule() = module {

}

fun Application.module() {
  install(DefaultHeaders)
  install(Locations)
  install(ConditionalHeaders)

  install(Koin) {
    printLogger()

    modules(appModule())
  }

  install(Routing) {
    mainRouter()
  }
}
