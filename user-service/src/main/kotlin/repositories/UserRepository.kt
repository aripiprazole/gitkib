package com.lorenzoog.gitkib.userservice.repositories

import com.lorenzoog.gitkib.userservice.entities.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Class that provides users.
 */
@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {

  /**
   * Finds user by its username. Returns null if don't exists.
   *
   * @return user nullable.
   */
  fun findByUsername(username: String): User?

}
