package com.lorenzoog.gitkib.userservice.config

import com.lorenzoog.gitkib.userservice.entities.Privilege
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import kotlin.reflect.full.declaredMemberProperties

object DefaultUsers {
  const val JUST_LOGGED = "just-logged"
  const val ALL_PERMISSIONS = "all-permissions"
}

private val allPermissions = Privilege.Companion::class.declaredMemberProperties.map {
  SimpleGrantedAuthority(it.get(Privilege.Companion).toString())
}

@TestConfiguration
class SecurityTestConfig {

  @Bean
  @Primary
  fun userDetailsService(): UserDetailsService {
    val password = "fake password"

    val users = listOf(
      User(DefaultUsers.JUST_LOGGED, password, emptyList()),
      User(DefaultUsers.ALL_PERMISSIONS, password, allPermissions)
    )

    return InMemoryUserDetailsManager(users)
  }

}
