package com.lorenzoog.gitkib.userservice.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@TestConfiguration
class SecurityTestConfig {

  @Bean
  @Primary
  fun userDetailsService(): UserDetailsService {
    val user = User("just-logged", "fake password", emptyList())

    return InMemoryUserDetailsManager(listOf(
      user
    ))
  }

}
