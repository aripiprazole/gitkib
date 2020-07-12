package com.lorenzoog.gitkib.userservice.security.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

const val AUTHENTICATION_HEADER = "Authorization"
const val TOKEN_PREFIX = "Bearer "

@Component
class JwtAuthenticationFilter(
  private val jwtAlgorithm: Algorithm,

  authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {

  private val securityContext: SecurityContext = SecurityContextHolder.getContext()

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    val authorizationHeader: String? = request.getHeader(AUTHENTICATION_HEADER)

    if (authorizationHeader != null) {
      securityContext.authentication = getAuthenticationFromJwtToken(authorizationHeader.replace(TOKEN_PREFIX, ""))
    }

    chain.doFilter(request, response)
  }

  private fun getAuthenticationFromJwtToken(token: String): Authentication {
    val decodedJWT = JWT
      .require(jwtAlgorithm)
      .build()
      .verify(token)

    return UsernamePasswordAuthenticationToken(decodedJWT.subject, null, listOf())
  }

}
