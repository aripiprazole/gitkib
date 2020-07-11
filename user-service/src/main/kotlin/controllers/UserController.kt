package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

const val USER_PAGINATION_OFFSET = 15

/**
 * Class that provides the rest api routes.
 *
 * @param userRepository repository that provides users.
 */
@RestController
@RequestMapping("users")
@Suppress("unused")
class UserController(val userRepository: UserRepository) {

  /**
   * Provides all users that page [page] contains.
   *
   * @return the page that contains the users.
   */
  @GetMapping
  fun index(@RequestParam(defaultValue = "0") page: Int): Page<User> {
    return userRepository.findAll(PageRequest.of(page, USER_PAGINATION_OFFSET))
  }

  /**
   * Provides the user with id [id].
   *
   * @return the user.
   */
  @GetMapping("{id}")
  fun show(@PathVariable id: Long): User {
    return userRepository.findById(id).orElseThrow(::ResourceNotFoundException)
  }

  /**
   * Updates the user with id [id].
   *
   * @return the user updated.
   */
  @PutMapping("{id}")
  fun update(@PathVariable id: Long, @RequestBody body: UserUpdateBody): User {
    val user = userRepository
      .findById(id)
      .orElseThrow(::ResourceNotFoundException)
      .apply {
        body.email?.let { email = it }
        body.password?.let { password = it }
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
  @DeleteMapping("{id}")
  fun destroy(@PathVariable id: Long): ResponseEntity<Any> {
    userRepository.deleteById(id)

    return ResponseEntity
      .noContent()
      .build<Any>()
  }

}
