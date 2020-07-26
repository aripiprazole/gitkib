package com.lorenzoog.gitkib.userservice

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
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

  val url = "$host:$port"

  private val context = GenericApplicationContext().apply {
    beanDefinitions.initialize(this)

    refresh()
  }

  private val httpHandler = WebHttpHandlerBuilder
    .applicationContext(context)
    .build()

  private val httpServer = HttpServer
    .create()
//    TODO: add ssl
//    .secure { it.sslContext(SslContextBuilder.forServer()) }
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

fun main() {
  Application(beanDefinitions = defaultBeans).startAndAwait()
}
