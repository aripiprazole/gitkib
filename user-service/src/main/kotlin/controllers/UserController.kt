package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

const val USER_PAGINATION_OFFSET = 15

/**
 * Class that provides the rest api routes.
 *
 * @param userRepository repository that provides users.
 */
@RestController
@Suppress("unused")
class UserController(
  val userRepository: UserRepository,
  val passwordEncoder: PasswordEncoder
) {

  companion object {
    const val INDEX_ENDPOINT = "/users"
    const val STORE_ENDPOINT = "/users"
    const val SHOW_ENDPOINT = "/users/{id}"
    const val UPDATE_ENDPOINT = "/users/{id}"
    const val DESTROY_ENDPOINT = "/users/{id}"
  }

  /**
   * Provides all users that page [page] contains.
   *
   * @return the page that contains the users.
   */
  @GetMapping(INDEX_ENDPOINT)
  fun index(@RequestParam(defaultValue = "0") page: Int): Page<User> {
    return userRepository.findAll(PageRequest.of(page, USER_PAGINATION_OFFSET))
  }

  /**
   * Provides the user with id [id].
   *
   * @return the user.
   */
  @GetMapping(SHOW_ENDPOINT)
  fun show(@PathVariable id: Long): User {
    return userRepository.findById(id).orElseThrow(::ResourceNotFoundException)
  }

  /**
   * Creates a new user with data provided in [body].
   *
   * @return the user created.
   */
  @PostMapping(STORE_ENDPOINT)
  fun store(@Valid @RequestBody body: UserCreateBody): User {
    return userRepository.save(User(
      id = 0L,
      email = body.email,
      username = body.username,
      password = passwordEncoder.encode(body.password)
    ))
  }

  /**
   * Updates the user with id [id].
   *
   * @return the user updated.
   */
  @PutMapping(UPDATE_ENDPOINT)
  fun update(@PathVariable id: Long, @Valid @RequestBody body: UserUpdateBody): User {
    val user = userRepository
      .findById(id)
      .orElseThrow(::ResourceNotFoundException)
      .apply {
        body.email?.let { email = it }
        body.password?.let { password = passwordEncoder.encode(it) }
        body.username?.let { username = it }
      }

    userRepository.save(user)

    return user
  }

  /**
   * Deletes from database the user with id [id]
   *
   * @return a no content response.
   */
  @DeleteMapping(DESTROY_ENDPOINT)
  fun destroy(@PathVariable id: Long): ResponseEntity<Any> {
    userRepository.deleteById(id)

    return ResponseEntity
      .noContent()
      .build<Any>()
  }


  /**
   * Handles the [ResourceNotFoundException], that is thrown when couldn't find user with that id.
   *
   * @return [Unit] nothing.
   */
  @ResponseStatus(value = NOT_FOUND, reason = "Could'nt find the user with that id.")
  @ExceptionHandler(EntityNotFoundException::class)
  fun onResourceNotFoundException() {
    // Automatic handling.
  }

  /**
   * Handles a generic exception.
   *
   * @return [ResponseEntity] response with 500 status.
   */
  @ExceptionHandler(Exception::class)
  fun onGenericException(exception: Exception): ResponseEntity<*> {
    return ResponseEntity
      .status(INTERNAL_SERVER_ERROR)
      .body(mapOf(
        "message" to exception.message,
        "stack" to exception.stackTrace.toList()
      ))
  }

}
