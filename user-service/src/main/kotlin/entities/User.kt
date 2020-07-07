package com.lorenzoog.gitkib.userservice.entities

import com.lorenzoog.gitkib.userservice.tables.UserTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Long>) : LongEntity(id) {

    val username: String by UserTable.username
    val email: String by UserTable.email
    val password: String by UserTable.password

}