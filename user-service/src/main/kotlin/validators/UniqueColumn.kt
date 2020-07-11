package com.lorenzoog.gitkib.userservice.validators

import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Target(allowedTargets = [AnnotationTarget.PROPERTY])
annotation class UniqueColumn(
  val column: String,
  val message: String
)

@Component
class UniqueColumnValidator(private val userRepository: UserRepository) : ConstraintValidator<UniqueColumn, String> {
  override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
    return userRepository.findByUsername(value) == null
  }
}
