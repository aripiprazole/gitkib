package com.lorenzoog.gitkib.commons.database.repositories

import org.jetbrains.exposed.sql.SizedIterable

interface Repository<ID, T> {

  fun findById(id: ID): T

  fun deleteById(id: ID)

  fun findAll(): SizedIterable<T>

  fun create(builder: T.() -> Unit): T

  fun paginate(page: Int, offset: Int): SizedIterable<T>

}
