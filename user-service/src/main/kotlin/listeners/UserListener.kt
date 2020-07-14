package com.lorenzoog.gitkib.userservice.listeners

import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.repositories.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.PostPersist

/**
 * Class that handle the user events
 *
 * @param profileRepository
 */
@Component
class UserListener(@Autowired val profileRepository: ProfileRepository) {

  /**
   * Handles the post persist event on users and automatically create an profile binding to user
   *
   * @param user the user that was persisted
   * @return [Unit]
   */
  @PostPersist
  fun onPostPersist(user: User) {
    profileRepository.save(Profile(
      id = 0L,
      name = user.username,
      user = user
    ))
  }

}
