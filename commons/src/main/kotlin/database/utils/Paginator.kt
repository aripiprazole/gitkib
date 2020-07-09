package com.lorenzoog.gitkib.commons.database.utils

import org.jetbrains.exposed.sql.SizedIterable

fun <T> SizedIterable<T>.paginate(page: Int, offset: Int): SizedIterable<T> {
  val currentOffset = page * offset

  return limit(currentOffset, (currentOffset + offset).toLong())
}
