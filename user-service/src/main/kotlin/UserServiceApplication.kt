package com.lorenzoog.gitkib.userservice

import com.lorenzoog.gitkib.userservice.discovery.setupConsulDiscovery
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.lorenzoog.gitkib.userservice.repositories")
@EnableAsync
@EnableScheduling
class UserServiceApplication {

  @Bean("healthChecker")
  fun healthChecker(): TaskScheduler = ThreadPoolTaskScheduler()

}

fun main(args: Array<String>) {
  setupConsulDiscovery()

  runApplication<UserServiceApplication>(*args)
}
