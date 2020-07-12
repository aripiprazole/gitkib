package com.lorenzoog.gitkib.userservice.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.gitkib.userservice.bodies.UserAuthenticateBody
import com.lorenzoog.gitkib.userservice.security.auth.UsernameUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.validation.Valid

const val JWT_EXPIRES_AT: Long =
  1000 * // second
    60 * // minute
    60 * // hour
    24 * // day
    7 // week

@RestController
class AuthController(
  private val authenticationManager: AuthenticationManager,

  private val jwtAlgorithm: Algorithm
) {

  @Value("\${jwt.issuer}")
  private lateinit var jwtIssuer: String

  @PostMapping("/login")
  fun authenticate(@Valid @RequestBody body: UserAuthenticateBody): ResponseEntity<Map<String, String>> {
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

    return ResponseEntity.ok(mapOf(
      "token" to jwtToken
    ))
  }

}
