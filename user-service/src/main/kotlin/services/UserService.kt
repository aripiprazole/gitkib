package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.dto.Page
import com.lorenzoog.gitkib.userservice.dto.UserUpdateDto
import com.lorenzoog.gitkib.userservice.entities.User

interface UserService {
  fun findPaginated(page: Int, size: Int = 15): Page<User>
  fun findById(id: Int): User
  fun updateById(id: Int, updateDto: UserUpdateDto): User
  fun create(data: User.() -> Unit)
  fun deleteById(id: Int)
}
