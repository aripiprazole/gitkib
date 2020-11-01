package com.lorenzoog.gitkib.userservice.services

import io.ktor.application.Application

interface DatabaseService {

  fun Application.connect()

  suspend fun createSchemas()

}
