package com.lorenzoog.gitkib.userservice.tests

import com.lorenzoog.gitkib.userservice.module
import io.ktor.application.Application
import io.ktor.config.MapApplicationConfig
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.DI
import org.kodein.di.ktor.di
import org.slf4j.LoggerFactory

private val job = Job()
private val scope = CoroutineScope(job + Dispatchers.Default)

class TestApplicationBuilder(private val app: Application) {
  fun kodein(builder: DI.MainBuilder.() -> Unit) {
    app.di(builder)
  }

  @OptIn(KtorExperimentalAPI::class)
  fun config(builder: MapApplicationConfig.() -> Unit) {
    (app.environment.config as MapApplicationConfig).apply(builder)
  }
}

@KtorExperimentalAPI
fun createApplication(
  host: String = "localhost",
  port: Int = 8000,
  builder: TestApplicationBuilder.() -> Unit = {}
): ApplicationEngine = embeddedServer(Netty, applicationEngineEnvironment {
  parentCoroutineContext = scope.coroutineContext
  log = LoggerFactory.getLogger("ktor.application")
  watchPaths = emptyList()

  module {
    TestApplicationBuilder(this).apply(builder)

    module()
  }

  connector {
    this.port = port
    this.host = host
  }
})
