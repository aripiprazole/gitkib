package com.lorenzoog.gitkib.userservice.utils

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

@Suppress("NOTHING_TO_INLINE")
inline fun <ID : Comparable<ID>, T : Entity<ID>, C : EntityClass<ID, T>> C.findOne(
  noinline builder: SqlExpressionBuilder.() -> Op<Boolean>
): T {
  return find(builder).first()
}
