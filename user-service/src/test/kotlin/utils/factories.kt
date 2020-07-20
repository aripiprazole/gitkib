package com.lorenzoog.gitkib.userservice.utils

import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.entities.Role
import com.lorenzoog.gitkib.userservice.entities.User
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction

fun User.Companion.mock(block: User.() -> Unit = {}) = transaction {
  User.new {
    username = "Jubiscreudi"
    password = "nou32h79"
    email = "email@org.com"
    roles = SizedCollection()
  }.apply(block)
}

fun Role.Companion.mock(block: Role.() -> Unit = {}) = transaction {
  Role.new {
    name = "Cargo fuck :sunglasses:"
    privileges = SizedCollection()
  }.apply(block)
}

fun Privilege.Companion.mock(block: Privilege.() -> Unit = {}) = transaction {
  Privilege.new {
    name = "pode.dar.o.cu"
    roles = SizedCollection()
  }.apply(block)
}

fun Profile.Companion.mock(user: User, block: Profile.() -> Unit = {}) = transaction {
  Profile.new {
    name = "Jubiscreudi da Silva"
    publicEmail = "contact@jusbiscreudi.silva"
    location = "Sem ideia, foda-sekkkkkkkkkkkkk"
    discordUsername = "Jubiscreudi#2442"
    twitterUsername = "Jubiscreudi_"
    websiteUrl = "jubiscreudi.com"
    company = "Jubiscreudi Inc."
  }.apply(block)
}
