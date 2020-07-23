package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.configs.defaultBeans
import com.lorenzoog.gitkib.userservice.discovery.setupConsulDiscovery
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.kotlin.coroutine.EnableCoroutine
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@Configuration
@EnableAsync
@EnableScheduling
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableCoroutine
class UserServiceApplication

fun main(args: Array<String>) {
  setupConsulDiscovery()

  runApplication<UserServiceApplication>(*args) {
    addInitializers(defaultBeans)
  }
}
