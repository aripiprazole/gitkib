@file:OptIn(KtorExperimentalLocationsAPI::class)
@file:Suppress("unused")

package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.lorenzoog.gitkib.userservice.utils.value
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.Routing
import kotlinx.coroutines.runBlocking
import org.kodein.di.instance
import org.kodein.di.ktor.DIFeature
import org.kodein.di.ktor.di

fun Application.module() {
  install(DefaultHeaders)
  install(Locations)
  install(ConditionalHeaders)

  install(DIFeature) {
    import(kodeinModule(this@module))
  }

  with(di().value<DatabaseService>()) {
    connect()
    runBlocking { createSchemas() }
  }

  install(Routing) {
    mainRouter()
  }
}
