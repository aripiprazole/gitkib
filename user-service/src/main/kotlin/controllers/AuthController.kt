package com.lorenzoog.gitkib.userservice.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.gitkib.userservice.bodies.UserAuthenticateBody
import kotlinx.coroutines.flow.flow
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.reactive.function.server.*
import java.time.Instant
import java.util.*

const val JWT_EXPIRES_AT: Long =
  1000 * // second
    60 * // minute
    60 * // hour
    24 * // day
    7 // week

/**
 * Class that provides rest api routes for auth.
 *
 * @param authenticationManager current authentication manager
 * @param jwtAlgorithm application jwt algorithm
 */
class AuthController(
  private val authenticationManager: AuthenticationManager,

  private val jwtAlgorithm: Algorithm
) {

  companion object {
    const val AUTHENTICATE_ENDPOINT = "/login"
    const val REGISTER_ENDPOINT = "/register"
  }

  @Value("\${jwt.issuer}")
  private lateinit var jwtIssuer: String

  /**
   * Handles authentication, same as login.
   *
   * @return a response with the token
   */
  suspend fun authenticate(request: ServerRequest): ServerResponse {
    val body = request.awaitBody<UserAuthenticateBody>()

    val username = body.username
    val token = UsernamePasswordAuthenticationToken(username, body.password, emptyList())

    authenticationManager.authenticate(token) // if couldn't login, will throw an exception that will be handled by [onAuthenticationException]

    val now = Instant.now()

    val jwtToken =
      JWT.create()
        .withSubject(username)
        .withIssuedAt(Date.from(now))
        .withIssuer(jwtIssuer)
        .withExpiresAt(Date.from(now.plusMillis(JWT_EXPIRES_AT)))
        .sign(jwtAlgorithm)

    return ServerResponse
      .ok()
      .bodyAndAwait(flow {
        mapOf(
          "token" to jwtToken
        )
      })
  }

}
