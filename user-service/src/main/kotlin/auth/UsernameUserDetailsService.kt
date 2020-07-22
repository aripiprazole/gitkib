package com.lorenzoog.gitkib.userservice.auth

import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.services.UserProvider
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

open class UsernameUserDetailsService(private val userProvider: UserProvider) : UserDetailsService {

  override fun loadUserByUsername(username: String): UserDetails =
    runBlocking {
      val user = userProvider.findByUsername(username)
      val userPrivileges = mutableSetOf<Privilege>()

      user.roles.forEach { role ->
        role.privileges.forEach { privilege ->
          userPrivileges += privilege
        }
      }

      User(user.username, user.password, userPrivileges.map {
        SimpleGrantedAuthority(it.name)
      })
    }

}
