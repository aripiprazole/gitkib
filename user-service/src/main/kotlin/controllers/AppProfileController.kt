package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.ProfileUpdateBody
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.services.ProfileProvider
import com.lorenzoog.gitkib.userservice.services.update
import com.lorenzoog.gitkib.userservice.utils.await
import org.springframework.data.domain.Page
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.*

const val PROFILE_PAGINATION_OFFSET = 15

/**
 * Class that provides the rest api routes.
 */
@RestController
@Suppress("unused")
class AppProfileController(
  private val profileProvider: ProfileProvider
) {

  companion object {
    const val INDEX_ENDPOINT = "/profiles"
    const val SHOW_ENDPOINT = "/profiles/{id}"
    const val UPDATE_ENDPOINT = "/profiles/{id}"
  }

  /**
   * Provides all profiles that page [request] contains.
   *
   * @return the page that contains the profiles.
   */
  suspend fun index(request: ServerRequest): ServerResponse {
    val page = request.queryParam("page").orElse("0").toInt()

    return ServerResponse
      .ok()
      .body<Page<Profile>>(profileProvider.findAll(page, PROFILE_PAGINATION_OFFSET))
      .await()
  }

  /**
   * Provides the profile of user with id in [request].
   *
   * @return the profile.
   */
  suspend fun show(request: ServerRequest): ServerResponse {
    val id = request.pathVariable("id").toLong()

    return ServerResponse
      .ok()
      .body<Profile>(profileProvider.findByUserId(id))
      .await()
  }

  /**
   * Updates the profile of user with id in [request].
   *
   * @return the profile updated.
   */
  suspend fun update(request: ServerRequest): ServerResponse {
    val id = request.pathVariable("id").toLong()
    val body = request.awaitBody<ProfileUpdateBody>()

    return ServerResponse
      .ok()
      .body<Profile>(profileProvider
        .findByUserId(id)
        .update(body))
      .await()
  }


  /**
   * Handles the [ResourceNotFoundException], that is thrown when couldn't find profile with that id.
   *
   * @return [Unit] nothing.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could'nt find the profile with that id.")
  @ExceptionHandler(ResourceNotFoundException::class)
  fun onResourceNotFoundException() {
    // Automatic handling.
  }

}
