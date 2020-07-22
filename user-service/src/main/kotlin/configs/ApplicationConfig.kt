package com.lorenzoog.gitkib.userservice.configs

import com.lorenzoog.gitkib.userservice.auth.UsernameUserDetailsService
import com.lorenzoog.gitkib.userservice.services.UserProvider
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.security.core.userdetails.UserDetailsService

val defaultBeans = beans {
  bean<UserProvider>()

  bean<UserDetailsService>("userDetailService") {
    UsernameUserDetailsService(ref())
  }
}

@Configuration
class ApplicationConfig
