package com.lorenzoog.gitkib.userservice.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE

object ProfileTable : LongIdTable(name = "profiles") {

  val name = varchar(name = "name", length = 32)
  val publicEmail = varchar(name = "public_email", length = 32).nullable()
  val company = varchar(name = "company", length = 32).nullable()
  val websiteUrl = varchar(name = "website_url", length = 32).nullable()
  val twitterUsername = varchar(name = "twitter_username", length = 32).nullable()
  val discordUsername = varchar(name = "discord_username", length = 32).nullable()
  val location = varchar(name = "location", length = 32).nullable()
  val userId = long(name = "user_id").entityId().references(ref = UserTable.id, onDelete = CASCADE)

}
