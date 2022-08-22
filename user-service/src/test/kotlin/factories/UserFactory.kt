package me.devgabi.gitkib.userservice.tests.factories

import me.devgabi.gitkib.userservice.entities.User
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * TODO: add faker library
 */
class UserFactory : IFactory<User> {
  override fun createOne(builder: User.() -> Unit) = transaction {
    User.new {
      username = "Fake username"
      email = "Fake email"
      password = "Fake password"
    }.apply(builder)
  }

  override fun createMany(amount: Int, builder: User.() -> Unit) =
    (1..amount)
      .map { createOne(builder) }

  fun createWithPermissions(permissions: List<String>, builder: User.() -> Unit = {}) =
      createOne(builder).also {
        TODO("Not yet implemented the permission system!")
      }

}
