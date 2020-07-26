package com.lorenzoog.gitkib.userservice.services

import org.jetbrains.exposed.sql.Database

interface DatabaseService {

  fun connect(): Database

  fun createSchemas()

}
