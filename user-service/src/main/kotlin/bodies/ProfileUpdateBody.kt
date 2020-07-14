package com.lorenzoog.gitkib.userservice.bodies

import javax.validation.constraints.Size

data class ProfileUpdateBody(
  @Size(min = 8, max = 32)
  val name: String? = null,

  @Size(min = 8, max = 32)
  val publicEmail: String? = null,

  @Size(min = 8, max = 32)
  val company: String? = null,

  @Size(min = 8, max = 32)
  val websiteUrl: String? = null,

  @Size(min = 8, max = 32)
  val twitterUsername: String? = null,

  @Size(min = 8, max = 32)
  val discordUsername: String? = null,

  @Size(min = 8, max = 32)
  val location: String? = null
)
