package com.lorenzoog.gitkib.userservice.tests.utils

import com.lorenzoog.gitkib.userservice.entities.User
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.properties.Delegates

suspend inline fun <reified T> HttpClient.json(urlString: String, block: HttpRequestBuilder.() -> Unit = {}) =
  request<T>(urlString) {
    contentType(ContentType.Application.Json)
    accept(ContentType.Application.Json)
  }

suspend inline fun <reified T> HttpClient.requestAs(user: User, urlString: String, block: HttpRequestBuilder.() -> Unit = {}): T {
  val authorizationHeader: String by Delegates.notNull()

  return json(urlString) {
    header("Authorization", authorizationHeader)

    block()
  }
}
