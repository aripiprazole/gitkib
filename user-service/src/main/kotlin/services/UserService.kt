package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.dto.Page
import com.lorenzoog.gitkib.userservice.dto.UserUpdateDto
import com.lorenzoog.gitkib.userservice.entities.User

interface UserService {
  suspend fun findPaginated(page: Int, size: Int = 15): Page<User>
  suspend fun findById(id: Int): User
  suspend fun updateById(id: Int, updateDto: UserUpdateDto): User
  suspend fun create(data: User.() -> Unit)
  suspend fun deleteById(id: Int)
}
