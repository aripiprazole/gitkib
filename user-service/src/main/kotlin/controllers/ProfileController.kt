package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.ProfileUpdateBody
import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.repositories.ProfileRepository
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
 *
 * @param profileRepository the repository that provide profiles.
 */
@RestController
@Suppress("unused")
class ProfileController(
  private val profileRepository: ProfileRepository
) {

  companion object {
    const val INDEX_ENDPOINT = "/profiles"
    const val SHOW_ENDPOINT = "/profiles/{userId}"
    const val UPDATE_ENDPOINT = "/profiles/{userId}"
  }

  /**
   * Provides all profiles that page [page] contains.
   *
   * @return the page that contains the profiles.
   */
  @GetMapping(INDEX_ENDPOINT)
  fun index(@RequestParam(defaultValue = "0") page: Int): Page<Profile> {
    return profileRepository.findAll(PageRequest.of(page, PROFILE_PAGINATION_OFFSET))
  }

  /**
   * Provides the profile of user with id [userId].
   *
   * @return the profile.
   */
  @GetMapping(SHOW_ENDPOINT)
  fun show(@PathVariable userId: Long): Profile {
    return profileRepository.findByUserId(userId) ?: throw ResourceNotFoundException()
  }

  /**
   * Updates the profile of user with id [userId].
   *
   * @return the profile updated.
   */
  @PutMapping(UPDATE_ENDPOINT)
  @PreAuthorize("hasAuthority('${Privilege.UPDATE_PROFILE}')")
  fun update(@PathVariable userId: Long, @Valid @RequestBody body: ProfileUpdateBody): Profile {
    val profile =
      (profileRepository.findByUserId(userId) ?: throw ResourceNotFoundException())
        .apply {
          body.name?.let { company = it }
          body.websiteUrl?.let { websiteUrl = it }
          body.publicEmail?.let { publicEmail = it }
          body.company?.let { company = it }
          body.discordUsername?.let { discordUsername = it }
          body.twitterUsername?.let { twitterUsername = it }
          body.location?.let { location = it }
        }

    profileRepository.save(profile)

    return profile
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
