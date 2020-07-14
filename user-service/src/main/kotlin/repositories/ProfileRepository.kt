package com.lorenzoog.gitkib.userservice.repositories

import com.lorenzoog.gitkib.userservice.entities.Profile
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Class that provide profiles.
 */
@Repository
interface ProfileRepository : PagingAndSortingRepository<Profile, Long> {

  /**
   * Find a profile with user_id [userId] and return null if don't exists
   *
   * @return the profile or null
   */
  fun findByUserId(userId: Long): Profile?

}
