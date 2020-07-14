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
)
