package com.lorenzoog.gitkib.userservice.entities

import javax.persistence.*
import javax.persistence.GenerationType.TABLE

@Entity
@Table(name = "roles")
data class Role(
  @Id
  @GeneratedValue(strategy = TABLE)
  val id: Long,

  @Column(length = 32, unique = true, columnDefinition = "text")
  val name: String,

  @ManyToMany(mappedBy = "roles")
  val users: MutableSet<User>,

  @ManyToMany
  @JoinTable(
    name = "role_privilege",
    joinColumns = [JoinColumn(name = "role_id")],
    inverseJoinColumns = [JoinColumn(name = "privilege_id")]
  )
  val privileges: MutableSet<Privilege>
)
