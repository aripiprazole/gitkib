package com.lorenzoog.gitkib.userservice

import kotlinx.coroutines.flow.flowOf
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference

class Application(
  private val host: String = "127.0.0.1",
  private val port: Int = 8080,

  private val beanDefinitions: BeanDefinitionDsl
) {

  private val context = GenericApplicationContext {
    beanDefinitions.initialize(this)

    refresh()
  }

  private val httpHandler = WebHttpHandlerBuilder
    .applicationContext(context)
    .build()

  private val httpServer = HttpServer
    .create()
    .host(host)
    .port(port)

  private val disposableReference = AtomicReference<DisposableServer>()

  fun start() {
    disposableReference.set(
      httpServer.handle(ReactorHttpHandlerAdapter(httpHandler))
        .bindNow()
    )
  }

  fun startAndAwait() {
    httpServer.handle(ReactorHttpHandlerAdapter(httpHandler))
      .bindUntilJavaShutdown(Duration.ofSeconds(45)) {
        println("Web server started! Accepting requests on https://$host:$port.")
      }
  }

  fun stop() {
    disposableReference.get().dispose()
  }

}

private val defaultBeans = beans {
  bean("webHandler") {
    RouterFunctions.toWebHandler(
      coRouter {
        GET("/") {
          ServerResponse.ok()
            .bodyAndAwait(flowOf("Hello, world"))
        }
      }
    )
  }
}

fun main() {
  Application(beanDefinitions = defaultBeans).startAndAwait()
}
