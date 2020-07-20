package com.lorenzoog.gitkib.userservice.entities

import com.lorenzoog.gitkib.userservice.tables.PrivilegeTable
import com.lorenzoog.gitkib.userservice.tables.RolePrivilegeTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

class Privilege(id: EntityID<Long>) : LongEntity(id) {

  var name: String by PrivilegeTable.name

  var roles: SizedIterable<Role> by Role via RolePrivilegeTable

  /**
   * Default privileges
   */
  companion object : LongEntityClass<Privilege>(PrivilegeTable) {
    const val VIEW_USER = "users.view"
    const val UPDATE_USER = "users.update"
    const val DELETE_USER = "users.destroy"

    const val UPDATE_PROFILE = "profiles.update"
  }
}
