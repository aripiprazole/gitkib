package com.lorenzoog.gitkib.userservice.serializers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.lorenzoog.gitkib.userservice.entities.User

class UserSerializer : StdSerializer<User>(User::class.java) {
  override fun serialize(user: User, gen: JsonGenerator, provider: SerializerProvider) {
    gen.writeStartObject()

    gen.writeNumberField("id", user.id.value)
    gen.writeStringField("username", user.username)
    gen.writeStringField("email", user.email)
    gen.writeObjectField("profile", runCatching { user.profile }.getOrNull())

    gen.writeEndObject()
  }

}
