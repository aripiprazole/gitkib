package com.lorenzoog.gitkib.userservice.utils

import org.springframework.kotlin.coroutine.web.awaitFirstOrNull
import reactor.core.publisher.Mono

suspend fun <T> Mono<T>.await() = awaitFirstOrNull()!!
