package com.lorenzoog.gitkib.userservice.security

import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.gitkib.userservice.controllers.AuthController
import com.lorenzoog.gitkib.userservice.controllers.AppProfileController
import com.lorenzoog.gitkib.userservice.security.auth.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
  @Value("\${jwt.secret}")
  private lateinit var jwtSecret: String

  @Value("\${password.encoder.strength}")
  private var passwordEncoderStrength = 0

  @Autowired
  fun configureGlobal(auth: AuthenticationManagerBuilder, usernameUserDetailsService: UserDetailsService) {
    auth.userDetailsService(usernameUserDetailsService)
  }

  override fun configure(http: HttpSecurity) {
    http.cors()
      .and().csrf().disable()

      .authorizeRequests()

      .antMatchers(GET, AppProfileController.INDEX_ENDPOINT, AppProfileController.SHOW_ENDPOINT).permitAll()

      .antMatchers(POST, AuthController.AUTHENTICATE_ENDPOINT, AuthController.REGISTER_ENDPOINT).permitAll()

      .anyRequest().authenticated()

      .and().addFilter(JwtAuthenticationFilter(jwtAlgorithm(), authenticationManager()))

      .sessionManagement().sessionCreationPolicy(STATELESS)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(passwordEncoderStrength)

  @Bean
  fun jwtAlgorithm(): Algorithm = Algorithm.HMAC512(jwtSecret)

  @Bean("authenticationManager")
  override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}
