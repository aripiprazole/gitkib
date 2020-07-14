package com.lorenzoog.gitkib.userservice.security.auth

import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
class UsernameUserDetailsService(
  @Autowired
  private val userRepository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails {
    val user = userRepository.findByUsername(username) ?: throw ResourceNotFoundException()
    val userPrivileges = mutableSetOf<Privilege>()

    user.roles.forEach { role ->
      role.privileges.forEach { privilege ->
        userPrivileges += privilege
      }
    }

    return User(user.username, user.password, userPrivileges.map {
      SimpleGrantedAuthority(it.name)
    })
  }

}
