package com.lorenzoog.gitkib.userservice.tests.factories

import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.tables.Users
import org.jetbrains.exposed.dao.id.EntityID

class UserFactory : Factory<User> {
  override fun createMany(amount: Int) =
    (1..amount)
      .map { createOne() }
      .toSet()

  override fun createOne(builder: User.() -> Unit) =
    User(EntityID(0L, Users)).apply {
      username = "fake username"
      email = "fake email"
      password = "fake password"
    }.apply(builder)
}
