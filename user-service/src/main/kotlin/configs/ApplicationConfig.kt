package com.lorenzoog.gitkib.userservice.configs

import com.lorenzoog.gitkib.userservice.auth.UsernameUserDetailsService
import com.lorenzoog.gitkib.userservice.services.UserProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.security.core.userdetails.UserDetailsService

val job = Job()
val coroutineScope = CoroutineScope(job + Dispatchers.Default)

val defaultBeans = beans {
  bean<UserProvider>()

  bean<UserDetailsService>("userDetailService") {
    UsernameUserDetailsService(ref())
  }

  bean<TaskScheduler>("healthChecker") {
    ThreadPoolTaskScheduler()
  }

  bean { coroutineScope }
}

@Configuration
class ApplicationConfig
