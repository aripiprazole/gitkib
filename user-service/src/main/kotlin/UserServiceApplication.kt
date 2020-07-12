package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.discovery.setupConsulDiscovery
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.lorenzoog.gitkib.userservice.repositories")
class UserServiceApplication

fun main(args: Array<String>) {
  setupConsulDiscovery()

  runApplication<UserServiceApplication>(*args)
}
