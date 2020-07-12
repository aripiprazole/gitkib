package com.lorenzoog.gitkib.userservice.security.auth

import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class UsernameUserDetailsService(
  @Autowired
  private val userRepository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    val user = userRepository.findByUsername(username) ?: throw ResourceNotFoundException()

    return User(user.username, user.password, emptyList())
  }

}
