package com.lorenzoog.gitkib.userservice.entities

import javax.persistence.*
import javax.persistence.CascadeType.REMOVE
import javax.persistence.GenerationType.AUTO

@Entity
data class Profile(
  @Id
  @GeneratedValue(strategy = AUTO)
  val id: Long,

  @Column(length = 32)
  var name: String,

  @Column(name = "public_email", nullable = true, length = 32)
  var publicEmail: String? = null,

  @Column(nullable = true, length = 32)
  var company: String? = null,

  @Column(name = "website_url", nullable = true, length = 32)
  var websiteUrl: String? = null,

  @Column(name = "twitter_username", nullable = true, length = 32)
  var twitterUsername: String? = null,

  @Column(name = "discord_username", nullable = true, length = 32)
  var discordUsername: String? = null,

  @Column(nullable = true, length = 32)
  var location: String? = null,

  @OneToOne(mappedBy = "profile", cascade = [REMOVE])
  val user: User
)
