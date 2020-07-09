package com.lorenzoog.gitkib.commons.utils

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.features.BadRequestException
import javax.validation.Validation

private val validator = Validation
  .buildDefaultValidatorFactory()
  .validator

private val objectMapper = ObjectMapper()

/**
 * Validator class
 */
abstract class Validator {

  /**
   * Validate that if request is valid, if ins't, throw exception that should be handled by ktor
   *
   * @return [Unit]
   */
  fun validate() {
    val validated = validator.validate(this)
    if (validated.isEmpty()) return

    val responseObject = mapOf(
      "message" to "Your request has errors, please check your payload.",
      "errors" to validated.map { violation ->
        violation.message
      }
    )

    throw BadRequestException(objectMapper.writeValueAsString(responseObject))
  }
}
