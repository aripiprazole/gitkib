package com.lorenzoog.gitkib.userservice.security

import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.gitkib.userservice.security.auth.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import kotlin.properties.Delegates

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
  @Value("\${jwt.secret}")
  private lateinit var jwtSecret: String

  @delegate:Value("\${password.encoder.strength}")
  private var passwordEncoderStrength by Delegates.notNull<Int>()

  @Autowired
  fun configureGlobal(auth: AuthenticationManagerBuilder, usernameUserDetailsService: UserDetailsService) {
    auth.userDetailsService(usernameUserDetailsService)
  }

  override fun configure(http: HttpSecurity) {
    http.cors()
      .and().csrf().disable()

      .authorizeRequests()

      .anyRequest().authenticated()

      .and().addFilter(JwtAuthenticationFilter(jwtAlgorithm(), authenticationManager()))

      .sessionManagement().sessionCreationPolicy(STATELESS)
  }

  @Bean
  fun passwordEncoder() = BCryptPasswordEncoder(passwordEncoderStrength)

  @Bean
  fun jwtAlgorithm(): Algorithm = Algorithm.HMAC512(jwtSecret)

}
