package com.lorenzoog.gitkib.userservice

import kotlinx.coroutines.flow.flowOf
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

val defaultBeans = beans {
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
