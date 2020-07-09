package com.lorenzoog.gitkib.commons.database

import io.github.cdimascio.dotenv.Dotenv
import org.jetbrains.exposed.sql.Database

/**
 * Database connector
 */
interface DatabaseService {

  /**
   * This will connect database with env provided with [environment]
   *
   * @return [Database]
   */
  fun connect(environment: Dotenv): Database

}
