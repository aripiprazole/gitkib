package com.lorenzoog.gitkib.userservice.security

import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.gitkib.userservice.security.auth.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

  @Value("\${jwt.secret}")
  private lateinit var jwtSecret: String

  override fun configure(http: HttpSecurity) {
    http.cors()
      .and().csrf().disable()

      .authorizeRequests()

      .anyRequest().authenticated()

      .and().addFilter(JwtAuthenticationFilter(jwtAlgorithm(), authenticationManager()))

      .sessionManagement().sessionCreationPolicy(STATELESS)
  }

  private fun jwtAlgorithm() = Algorithm.HMAC512(jwtSecret)

}
