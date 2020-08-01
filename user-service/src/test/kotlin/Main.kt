package com.lorenzoog.gitkib.userservice.tests

import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.withTestApplication

import io.ktor.config.MapApplicationConfig
import io.ktor.util.KtorExperimentalAPI

import com.lorenzoog.gitkib.userservice.module

@OptIn(KtorExperimentalAPI::class)
fun testApplication(block: TestApplicationEngine.() -> Unit) {
  withTestApplication({
    (environment.config as MapApplicationConfig).apply {
      // add default env values.
    }

    module()
  }, block)
}
