package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.controllers.AuthController.Companion.REGISTER_ENDPOINT
import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserProvider
import com.lorenzoog.gitkib.userservice.services.update
import org.jetbrains.exposed.sql.SizedCollection
import org.springframework.data.domain.Page
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

const val USER_PAGINATION_OFFSET = 15

/**
 * Class that provides the rest api routes.
 *
 * @param userProvider class that provide the users.
 */
@RestController
@Suppress("unused")
class UserController(
  val userProvider: UserProvider,
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
  @PreAuthorize("hasAuthority('${Privilege.VIEW_USER}')")
  suspend fun index(@RequestParam(defaultValue = "0") page: Int): Page<User> {
    return userProvider.findAll(page, USER_PAGINATION_OFFSET)
  }

  /**
   * Provides the user with id [id].
   *
   * @return the user.
   */
  @GetMapping(SHOW_ENDPOINT)
  @PreAuthorize("hasAuthority('${Privilege.VIEW_USER}')")
  suspend fun show(@PathVariable id: Long): User {
    return userProvider.findById(id)
  }

  /**
   * Creates a new user with data provided in [body].
   *
   * @return the user created.
   */
  @PostMapping(STORE_ENDPOINT, REGISTER_ENDPOINT)
  suspend fun store(@Valid @RequestBody body: UserCreateBody): User {
    return userProvider.save {
      email = body.email
      username = body.username
      password = passwordEncoder.encode(body.password)
      roles = SizedCollection()
    }
  }

  /**
   * Updates the user with id [id].
   *
   * @return the user updated.
   */
  @PutMapping(UPDATE_ENDPOINT)
  @PreAuthorize("hasAuthority('${Privilege.UPDATE_USER}')")
  suspend fun update(@PathVariable id: Long, @Valid @RequestBody body: UserUpdateBody): User {
    return userProvider
      .findById(id)
      .update(passwordEncoder, body)
  }

  /**
   * Deletes from database the user with id [id]
   *
   * @return a no content response.
   */
  @DeleteMapping(DESTROY_ENDPOINT)
  @PreAuthorize("hasAuthority('${Privilege.DELETE_USER}')")
  suspend fun destroy(@PathVariable id: Long): ResponseEntity<Any> {
    userProvider.deleteById(id)

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
  @ExceptionHandler(ResourceNotFoundException::class)
  fun onResourceNotFoundException() {
    // Automatic handling.
  }

}
