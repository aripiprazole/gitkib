package com.lorenzoog.gitkib.userservice.tests.factories

import com.lorenzoog.gitkib.userservice.entities.User
import org.jetbrains.exposed.sql.transactions.transaction

class UserFactory : Factory<User> {
  override fun createMany(amount: Int) = transaction {
    (1..amount)
      .map { createOne() }
      .toSet()
  }

  override fun createOne(builder: User.() -> Unit) = transaction {
    User.new {
      username = "fake username"
      password = "fake password"
      email = "fake email"
    }.apply(builder)
  }
}
