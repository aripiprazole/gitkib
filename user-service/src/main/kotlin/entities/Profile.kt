package com.lorenzoog.gitkib.userservice.entities

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.lorenzoog.gitkib.userservice.serializers.ProfileSerializer
import com.lorenzoog.gitkib.userservice.tables.ProfileTable
import com.lorenzoog.gitkib.userservice.tables.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

@JsonSerialize(using = ProfileSerializer::class)
class Profile(id: EntityID<Long>): LongEntity(id) {
  var name: String by ProfileTable.name

  var publicEmail: String? by ProfileTable.publicEmail

  var company: String? by ProfileTable.company

  var websiteUrl: String? by ProfileTable.websiteUrl

  var twitterUsername: String? by ProfileTable.twitterUsername

  var discordUsername: String? by ProfileTable.discordUsername

  var location: String? by ProfileTable.location

  var user: User? by User optionalReferencedOn ProfileTable.userId

  companion object : LongEntityClass<Profile>(UserTable)
}
