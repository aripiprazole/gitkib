package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.ProfileUpdateBody
import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.utils.findAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException
import javax.validation.Valid

const val PROFILE_PAGINATION_OFFSET = 15

/**
 * Class that provides the rest api routes.
 */
@RestController
@Suppress("unused")
class AppProfileController {

  companion object {
    const val INDEX_ENDPOINT = "/profiles"
    const val SHOW_ENDPOINT = "/profiles/{id}"
    const val UPDATE_ENDPOINT = "/profiles/{id}"
  }

  /**
   * Provides all profiles that page [page] contains.
   *
   * @return the page that contains the profiles.
   */
  @GetMapping(INDEX_ENDPOINT)
  fun index(@RequestParam(defaultValue = "0") page: Int): Page<Profile> = transaction {
    Profile.findAll(PageRequest.of(page, PROFILE_PAGINATION_OFFSET))
  }

  /**
   * Provides the profile of user with id [id].
   *
   * @return the profile.
   */
  @GetMapping(SHOW_ENDPOINT)
  fun show(@PathVariable id: Long): Profile = transaction {
    Profile.findById(id) ?: throw ResourceNotFoundException()
  }

  /**
   * Updates the profile of user with id [id].
   *
   * @return the profile updated.
   */
  @PutMapping(UPDATE_ENDPOINT)
  @PreAuthorize("hasAuthority('${Privilege.UPDATE_PROFILE}')")
  fun update(@PathVariable id: Long, @Valid @RequestBody body: ProfileUpdateBody) = transaction {
    (Profile.findById(id) ?: throw ResourceNotFoundException())
      .apply {
        body.name?.let { company = it }
        body.websiteUrl?.let { websiteUrl = it }
        body.publicEmail?.let { publicEmail = it }
        body.company?.let { company = it }
        body.discordUsername?.let { discordUsername = it }
        body.twitterUsername?.let { twitterUsername = it }
        body.location?.let { location = it }
      }
  }


  /**
   * Handles the [ResourceNotFoundException], that is thrown when couldn't find profile with that id.
   *
   * @return [Unit] nothing.
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could'nt find the profile with that id.")
  @ExceptionHandler(EntityNotFoundException::class)
  fun onResourceNotFoundException() {
    // Automatic handling.
  }

}
