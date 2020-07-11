package com.lorenzoog.gitkib.userservice.repositories

import com.lorenzoog.gitkib.userservice.entities.User
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Class that provides users.
 */
interface UserRepository : PagingAndSortingRepository<User, Long> {

  /**
   * Finds user by its username. Returns null if don't exists.
   *
   * @return user nullable.
   */
  fun findByUsername(username: String): User?

}
