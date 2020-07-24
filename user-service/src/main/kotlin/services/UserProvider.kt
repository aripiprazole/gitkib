package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.bodies.UserUpdateBody
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.exceptions.EntityNotFoundException
import com.lorenzoog.gitkib.userservice.tables.UserTable
import com.lorenzoog.gitkib.userservice.utils.findAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

suspend fun User.update(passwordEncoder: PasswordEncoder, body: UserUpdateBody): User {
  newSuspendedTransaction {
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
   * @throws EntityNotFoundException if couldn't find the entity with username [username].
   */
  suspend fun findByUsername(username: String) = newSuspendedTransaction {
    User.find { UserTable.username eq username }
      .limit(n = 1)
      .firstOrNull() ?: throw EntityNotFoundException()
  }

  override suspend fun findAll(page: Int, offset: Int) = newSuspendedTransaction {
    User.findAll(PageRequest.of(page, offset))
  }

  override suspend fun findById(id: Long) = newSuspendedTransaction {
    User.findById(id) ?: throw EntityNotFoundException()
  }

  override suspend fun save(entityBuilder: User.() -> Unit) = newSuspendedTransaction {
    User.new(entityBuilder)
  }

  override suspend fun deleteById(id: Long) = newSuspendedTransaction {
    findById(id).delete()
  }
}
