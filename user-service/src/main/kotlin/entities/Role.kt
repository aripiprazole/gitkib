package com.lorenzoog.gitkib.userservice.entities

import javax.persistence.*
import javax.persistence.GenerationType.TABLE

@Entity
@Table(name = "roles")
data class Role(
  @Id
  @GeneratedValue(strategy = TABLE)
  val id: Long,

  @Column(length = 32, unique = true)
  val name: String,

  @ManyToMany(mappedBy = "role")
  val users: MutableCollection<User>,

  @ManyToMany(mappedBy = "privilege", targetEntity = Privilege::class)
  val privileges: MutableCollection<Privilege>
)
