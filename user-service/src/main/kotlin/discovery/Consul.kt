package com.lorenzoog.gitkib.userservice.discovery

import com.orbitz.consul.Consul
import com.orbitz.consul.model.agent.ImmutableRegistration
import com.orbitz.consul.model.agent.Registration

const val SERVICE_ID = "user-service"
const val SERVICE_NAME = "user service"

fun setupConsulDiscovery() {
  val client = Consul.builder().build()
  val agentClient = client.agentClient()

  val serviceRegistration: Registration = ImmutableRegistration.builder()
    .id(SERVICE_ID)
    .name(SERVICE_NAME)
    .port(8080)
    .tags(listOf("user-service"))
    .meta(mapOf("version" to "1.0"))
    .build()

  agentClient.register(serviceRegistration)
}
