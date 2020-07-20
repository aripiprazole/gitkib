package com.lorenzoog.gitkib.userservice.utils

import com.lorenzoog.gitkib.userservice.entities.Privilege
import com.lorenzoog.gitkib.userservice.entities.Profile
import com.lorenzoog.gitkib.userservice.entities.Role
import com.lorenzoog.gitkib.userservice.entities.User
import org.jetbrains.exposed.sql.transactions.transaction

fun User.Companion.mock() = transaction {
  User.new {
    username = "Jubiscreudi"
    password = "nou32h79"
    email = "email@org.com"
  }
}

fun Role.Companion.mock() = transaction {
  Role.new {
    name = "Cargo fuck :sunglasses:"
  }
}

fun Privilege.Companion.mock() = transaction {
  Privilege.new {
    name = "pode.dar.o.cu"
  }
}

fun Profile.Companion.mock(user: User) = transaction {
  Profile.new {
    name = "Jubiscreudi da Silva"
    publicEmail = "contact@jusbiscreudi.silva"
    location = "Sem ideia, foda-sekkkkkkkkkkkkk"
    discordUsername = "Jubiscreudi#2442"
    twitterUsername = "Jubiscreudi_"
    websiteUrl = "jubiscreudi.com"
    company = "Jubiscreudi Inc."
  }
}
