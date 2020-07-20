package com.lorenzoog.gitkib.userservice.entities

import com.lorenzoog.gitkib.userservice.tables.RoleTable
import com.lorenzoog.gitkib.userservice.tables.RolePrivilegeTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

class Role(id: EntityID<Long>) : LongEntity(id) {

  var name: String by RoleTable.name

  var privileges: SizedIterable<Privilege> by Privilege via RolePrivilegeTable

  companion object : LongEntityClass<Role>(RoleTable)
}
