package com.lorenzoog.gitkib.userservice.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.lorenzoog.gitkib.userservice.entities.Profile

class ProfileSerializer : StdSerializer<Profile>(Profile::class.java) {
  override fun serialize(profile: Profile, gen: JsonGenerator, provider: SerializerProvider) {
    gen.writeStartObject()

    gen.writeStringField("name", profile.name)
    gen.writeStringField("public_email", profile.publicEmail)
    gen.writeStringField("location", profile.location)
    gen.writeStringField("twitter_username", profile.twitterUsername)
    gen.writeStringField("discord_username", profile.discordUsername)
    gen.writeStringField("company", profile.company)

    gen.writeEndObject()
  }
}
