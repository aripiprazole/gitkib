package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.bodies.ProfileUpdateBody
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.utils.findAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.data.domain.PageRequest
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

fun Profile.update(body: ProfileUpdateBody): Profile {
  transaction {
    body.name?.let { company = it }
    body.websiteUrl?.let { websiteUrl = it }
    body.publicEmail?.let { publicEmail = it }
    body.company?.let { company = it }
    body.discordUsername?.let { discordUsername = it }
    body.twitterUsername?.let { twitterUsername = it }
    body.location?.let { location = it }
  }

  return this
}

@Service
class ProfileProvider(private val userProvider: UserProvider) : EntityProvider<Profile> {

  /**
   * Return the entity by its user id and cached.
   *
   * @return user with id [id].
   * @throws ResourceNotFoundException if couldn't find the entity with id [id].
   */
  suspend fun findByUserId(id: Long) = newSuspendedTransaction {
    userProvider.findById(id).profile!!
  }

  override suspend fun findAll(page: Int, offset: Int) = newSuspendedTransaction {
    Profile.findAll(PageRequest.of(page, offset))
  }

  override suspend fun findById(id: Long) = newSuspendedTransaction {
    Profile.findById(id) ?: throw ResourceNotFoundException()
  }

  override suspend fun save(entityBuilder: Profile.() -> Unit) = newSuspendedTransaction {
    Profile.new(entityBuilder)
  }

  override suspend fun deleteById(id: Long) = newSuspendedTransaction {
    findById(id).delete()
  }
}
