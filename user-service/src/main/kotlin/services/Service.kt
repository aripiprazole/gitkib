package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.dtos.Page
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException

interface Service<T> {

  suspend fun findAll(page: Int, size: Int): Page<T>

  @Throws(EntityNotFoundException::class)
  suspend fun findById(id: Long): T

  @Throws(EntityNotFoundException::class)
  suspend fun deleteById(id: Long)

  suspend fun save(builder: T.() -> Unit): T

  @Throws(EntityNotFoundException::class)
  suspend fun updateById(id: Long, callback: T.() -> Unit): T

}
