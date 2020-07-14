package com.lorenzoog.gitkib.userservice.entities

import javax.persistence.*
import javax.persistence.GenerationType.TABLE

@Entity
@Table(name = "privileges")
data class Privilege(
  @Id
  @GeneratedValue(strategy = TABLE)
  val id: Long,

  @Column(length = 32, unique = true)
  var name: String,

  @ManyToMany(mappedBy = "privileges")
  var roles: MutableSet<Role>
) {

  /**
   * Default privileges
   */
  companion object {
    const val VIEW_USER = "@users/view"
    const val CREATE_USER = "@users/create"
    const val UPDATE_USER = "@users/update"
    const val DELETE_USER = "@users/destroy"
  }

}
