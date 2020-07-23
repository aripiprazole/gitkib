package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.UserCreateBody
import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserProvider
import com.lorenzoog.gitkib.userservice.services.update
import com.lorenzoog.gitkib.userservice.utils.await
import org.jetbrains.exposed.sql.SizedCollection
import org.springframework.data.domain.Page
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.*
import java.net.URI

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
   * Provides all users that page [request] contains.
   *
   * @return the page that contains the users.
   */
  suspend fun index(request: ServerRequest): ServerResponse {
    val page = request.queryParam("page").orElse("0").toInt()

    return ServerResponse
      .ok()
      .body<Page<User>>(userProvider.findAll(page, USER_PAGINATION_OFFSET))
      .await()
  }

  /**
   * Provides the user with id in [request].
   *
   * @return the user.
   */
  suspend fun show(request: ServerRequest): ServerResponse {
    val id = request.pathVariable("id").toLong()

    return ServerResponse
      .ok()
      .body<User>(userProvider.findById(id))
      .await()
  }

  /**
   * Creates a new user with data provided in [request].
   *
   * @return the user created.
   */
  suspend fun store(request: ServerRequest): ServerResponse {
    val body = request.awaitBody<UserCreateBody>()
    val user = userProvider.save {
      email = body.email
      username = body.username
      password = passwordEncoder.encode(body.password)
      roles = SizedCollection()
    }
    val createdAt = URI(SHOW_ENDPOINT.replace("{id}", user.id.toString()))

    return ServerResponse
      .created(createdAt)
      .body<User>(user)
      .await()
  }

  /**
   * Updates the user with id in [request].
   *
   * @return the user updated.
   */
  suspend fun update(request: ServerRequest): ServerResponse {
    val id = request.pathVariable("id").toLong()
    val body = request.awaitBody<UserUpdateBody>()

    return ServerResponse
      .accepted()
      .body<User>(userProvider
        .findById(id)
        .update(passwordEncoder, body))
      .await()
  }

  /**
   * Deletes from database the user with id in [request]
   *
   * @return a no content response.
   */
  suspend fun destroy(request: ServerRequest): ServerResponse {
    val id = request.pathVariable("id").toLong()

    userProvider.deleteById(id)

    return ServerResponse
      .noContent()
      .buildAndAwait()
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
