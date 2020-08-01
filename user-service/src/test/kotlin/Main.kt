package com.lorenzoog.gitkib.userservice.tests

import com.lorenzoog.gitkib.userservice.module
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.slf4j.LoggerFactory

private val job = Job()
private val scope = CoroutineScope(job + Dispatchers.Default)

@KtorExperimentalAPI
fun createApplication(
  host: String = "localhost",
  port: Int = 8000,
  env: MapApplicationConfig.() -> Unit
): ApplicationEngine = embeddedServer(Netty, applicationEngineEnvironment {
  parentCoroutineContext = scope.coroutineContext
  log = LoggerFactory.getLogger("ktor.application")
  watchPaths = emptyList()

  config = MapApplicationConfig().apply(env)

  module(Application::module)

  connector {
    this.port = port
    this.host = host
  }
})
