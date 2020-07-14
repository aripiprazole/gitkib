package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

fun User.update(passwordEncoder: PasswordEncoder, body: UserUpdateBody): User {
  body.email?.let { email = it }
  body.password?.let { password = passwordEncoder.encode(it) }
  body.username?.let { username = it }

  return this
}

@Service
class UserProvider(private val userRepository: UserRepository) : EntityProvider<User> {

  /**
   * Return the entity by its username and cached.
   *
   * @return user with username [username].
   * @throws ResourceNotFoundException if couldn't find the entity with username [username].
   */
  fun findByUsername(username: String): User {
    return userRepository.findByUsername(username) ?: throw ResourceNotFoundException()
  }

  override fun findAll(page: Int, offset: Int): Page<User> {
    return userRepository.findAll(PageRequest.of(page, offset))
  }

  override fun findById(id: Long): User {
    return userRepository.findById(id).orElseThrow(::ResourceNotFoundException)
  }

  override fun save(entity: User): User {
    return userRepository.save(entity)
  }

  override fun deleteById(id: Long) {
    return userRepository.deleteById(id)
  }
}
