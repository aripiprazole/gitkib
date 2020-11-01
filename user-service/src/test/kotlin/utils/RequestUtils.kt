package com.lorenzoog.gitkib.userservice.tests.utils

import com.lorenzoog.gitkib.userservice.entities.User
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.LazyDI

suspend inline fun <reified T> HttpClient.json(urlString: String, block: HttpRequestBuilder.() -> Unit = {}) =
  request<T>(urlString) {
    contentType(ContentType.Application.Json)
    accept(ContentType.Application.Json)

    block()
  }

suspend inline fun <reified T> HttpClient.json(
  method: HttpMethod,
  urlString: String,
  builder: HttpRequestBuilder.() -> Unit = {}
) = json<T>(urlString) {
  this.method = method

  builder()
}

suspend inline fun <reified T> HttpClient.request(
  method: HttpMethod,
  urlString: String,
  builder: HttpRequestBuilder.() -> Unit = {}
) = request<T>(urlString) {
  this.method = method

  builder()
}

class ActingAs(
  val httpClient: HttpClient,
  val user: User,

  override val di: DI
) : DIAware {
  val authorizationHeader: String
    get() = TODO()

  suspend inline fun <reified T> request(
    method: HttpMethod,
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
  ) = httpClient.json<T>(urlString) {
    header("Authorization", authorizationHeader)

    this.method = method

    block()
  }
}

fun HttpClient.actingAs(user: User, lazyDI: LazyDI) = ActingAs(this, user, lazyDI)
