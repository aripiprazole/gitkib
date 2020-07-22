package com.lorenzoog.gitkib.userservice.configs

import com.auth0.jwt.algorithms.Algorithm
import com.lorenzoog.gitkib.userservice.controllers.AppProfileController
import com.lorenzoog.gitkib.userservice.auth.JwtAuthenticationFilter
import com.lorenzoog.gitkib.userservice.auth.UsernameUserDetailsService
import com.lorenzoog.gitkib.userservice.services.UserProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.config.web.servlet.invoke
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
  private lateinit var userDetailsService: UserDetailsService

  override fun configure(http: HttpSecurity) {
    http {
      cors { }
      httpBasic { }
      csrf {
        disable()
      }

      authorizeRequests {
        authorize(AppProfileController.INDEX_ENDPOINT, permitAll)
        authorize(AppProfileController.SHOW_ENDPOINT, permitAll)

        authorize("**", authenticated)
      }

      sessionManagement {
        sessionCreationPolicy = STATELESS
      }

      http.addFilter(JwtAuthenticationFilter(jwtAlgorithm(), userDetailsService, authenticationManager()))
    }
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(passwordEncoderStrength)

  @Bean
  fun jwtAlgorithm(): Algorithm = Algorithm.HMAC512(jwtSecret)

  @Bean("authenticationManager")
  override fun authenticationManagerBean(): AuthenticationManager = super.authenticationManagerBean()

}
