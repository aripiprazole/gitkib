package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.dtos.Page
import com.lorenzoog.gitkib.userservice.entities.User
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException

class UserService : Service<User> {
  @Throws(EntityNotFoundException::class)
  suspend fun findByUsername(username: String): User {
    TODO("Not yet implemented")
  }

  override suspend fun findAll(page: Int, size: Int): Page<User> {
    TODO("Not yet implemented")
  }

  @Throws(EntityNotFoundException::class)
  override suspend fun findById(id: Long): User {
    TODO("Not yet implemented")
  }

  @Throws(EntityNotFoundException::class)
  override suspend fun deleteById(id: Long) {
    TODO("Not yet implemented")
  }

  override suspend fun save(builder: User.() -> Unit): User {
    TODO("Not yet implemented")
  }

  override suspend fun updateById(id: Long, callback: User.() -> Unit): User {
    TODO("Not yet implemented")
  }
}
