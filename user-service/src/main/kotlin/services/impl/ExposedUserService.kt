package me.devgabi.gitkib.userservice.services.impl

import me.devgabi.gitkib.userservice.dto.Page
import me.devgabi.gitkib.userservice.dto.UserUpdateDto
import me.devgabi.gitkib.userservice.entities.User
import me.devgabi.gitkib.userservice.services.UserService

class ExposedUserService : UserService{
  override suspend fun findPaginated(page: Int, size: Int): Page<User> {
    TODO("Not yet implemented")
  }

  override suspend fun findById(id: Long): User {
    TODO("Not yet implemented")
  }

  override suspend fun updateById(id: Long, updateDto: UserUpdateDto): User {
    TODO("Not yet implemented")
  }

  override suspend fun create(data: User.() -> Unit) {
    TODO("Not yet implemented")
  }

  override suspend fun deleteById(id: Long) {
    TODO("Not yet implemented")
  }

}
