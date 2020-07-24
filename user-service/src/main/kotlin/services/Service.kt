package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.dtos.Page
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException

interface Service<T> {

  fun findAll(page: Int, size: Int): Page<T>

  @Throws(EntityNotFoundException::class)
  fun findById(id: Long): T

  @Throws(EntityNotFoundException::class)
  fun deleteById(id: Long)

  fun save(builder: T.() -> Unit): T

}
