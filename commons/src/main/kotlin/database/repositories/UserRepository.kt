package com.lorenzoog.gitkib.commons.database.repositories

import com.lorenzoog.gitkib.commons.database.entities.User
import com.lorenzoog.gitkib.commons.database.utils.paginate
import io.ktor.features.NotFoundException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository(private val database: Database) : Repository<Long, User> {

  override fun findById(id: Long) = transaction(database) {
    User.findById(id) ?: throw NotFoundException()
  }

  override fun deleteById(id: Long) = transaction(database) {
    findById(id).delete()
  }

  override fun findAll() = transaction(database) {
    User.all()
  }

  override fun paginate(page: Int, offset: Int) = transaction(database) {
    User.all().paginate(page, offset)
  }

  override fun create(builder: User.() -> Unit) = transaction(database) {
    User.new(builder)
  }
}
