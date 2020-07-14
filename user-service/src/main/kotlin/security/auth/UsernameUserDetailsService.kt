package com.lorenzoog.gitkib.userservice.security.auth

import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.services.UserProvider
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import javax.transaction.Transactional

open class UsernameUserDetailsService(private val userProvider: UserProvider) : UserDetailsService {

  @Transactional
  override fun loadUserByUsername(username: String): UserDetails {
    val user = userProvider.findByUsername(username)
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
