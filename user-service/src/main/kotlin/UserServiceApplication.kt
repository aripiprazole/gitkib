package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.auth.UsernameUserDetailsService
import com.lorenzoog.gitkib.userservice.discovery.setupConsulDiscovery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.kotlin.coroutine.EnableCoroutine
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.core.userdetails.UserDetailsService

@SpringBootApplication
@Configuration
@EnableAsync
@EnableScheduling
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableCoroutine
class UserServiceApplication

private val job = Job()
private val coroutineScope = CoroutineScope(job + Dispatchers.Default)

fun main(args: Array<String>) {
  setupConsulDiscovery()

  runApplication<UserServiceApplication>(*args) {
    addInitializers(beans {
      bean<UserDetailsService>("userDetailService") {
        UsernameUserDetailsService(ref())
      }

      bean<TaskScheduler>("healthChecker") {
        ThreadPoolTaskScheduler()
      }

      bean("coroutineScope") { coroutineScope }
    })
  }
}
