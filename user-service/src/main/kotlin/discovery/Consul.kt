package com.lorenzoog.gitkib.userservice.discovery

import com.orbitz.consul.AgentClient
import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration
import com.orbitz.consul.model.agent.Registration.RegCheck
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

const val SERVICE_ID = "user-service"
const val SERVICE_NAME = "user service"
const val SERVICE_CHECK_TTL = 5L

private lateinit var client: Consul
private lateinit var agentClient: AgentClient

@EnableAsync
@EnableScheduling
@Component
@Suppress("unused")
class ConsulDiscovery {
  @Async("healthChecker")
  @Scheduled(fixedRate = SERVICE_CHECK_TTL)
  fun handleHealthCheck() {
    agentClient.pass(SERVICE_ID)
  }
}

fun setupConsulDiscovery() {
  client = Consul.builder().build()
  agentClient = client.agentClient()

  val serviceRegistration: Registration = ImmutableRegistration.builder()
    .id(SERVICE_ID)
    .name(SERVICE_NAME)
    .port(8080)
    .check(RegCheck.ttl(SERVICE_CHECK_TTL))
    .tags(listOf("user-service"))
    .meta(mapOf("version" to "1.0"))
    .build()

  agentClient.register(serviceRegistration)
}
