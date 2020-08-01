@file:OptIn(KtorExperimentalLocationsAPI::class)
@file:Suppress("unused")

package com.lorenzoog.gitkib.userservice

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ConditionalHeaders
import io.ktor.features.DefaultHeaders
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations

fun Application.module() {
  install(DefaultHeaders)
  install(Locations)
  install(ConditionalHeaders)
}
