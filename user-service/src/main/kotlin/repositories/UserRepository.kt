package com.lorenzoog.gitkib.userservice.repositories

import com.lorenzoog.gitkib.userservice.entities.User
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Class that provides users.
 */
interface UserRepository : PagingAndSortingRepository<User, Long>
