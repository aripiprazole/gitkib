package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.tables.UserTable
import com.lorenzoog.gitkib.userservice.utils.findAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.data.domain.PageRequest
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

fun User.update(passwordEncoder: PasswordEncoder, body: UserUpdateBody): User {
  transaction {
    body.email?.let { email = it }
    body.password?.let { password = passwordEncoder.encode(it) }
    body.username?.let { username = it }
  }

  return this
}

@Service
class UserProvider : EntityProvider<User> {

  /**
   * Return the entity by its username and cached.
   *
   * @return user with username [username].
   * @throws ResourceNotFoundException if couldn't find the entity with username [username].
   */
  fun findByUsername(username: String) = transaction {
    User.find { UserTable.username eq username }
      .limit(n = 1)
      .firstOrNull() ?: throw ResourceNotFoundException()
  }

  override fun findAll(page: Int, offset: Int) = transaction {
    User.findAll(PageRequest.of(page, offset))
  }

  override fun findById(id: Long) = transaction {
    User.findById(id) ?: throw ResourceNotFoundException()
  }

  override fun save(entityBuilder: User.() -> Unit) = transaction {
    User.new(entityBuilder)
  }

  override fun deleteById(id: Long) = transaction {
    findById(id).delete()
  }
}
