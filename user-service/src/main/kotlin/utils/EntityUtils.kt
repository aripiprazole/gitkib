package com.lorenzoog.gitkib.userservice.utils

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.SizedIterable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

fun <T : Entity<*>> EntityClass<*, T>.findAll(pageRequest: PageRequest): Page<T> {
  val page = pageRequest.offset * pageRequest.pageNumber

  return pageOf(
    content = all().limit(n = page.toInt(), offset = page + pageRequest.offset),
    pageable = pageRequest,
    total = count()
  )
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> pageOf(content: SizedIterable<T>, pageable: Pageable, total: Long) =
  PageImpl(
    content.toList(),
    pageable,
    total
  )

