package com.lorenzoog.gitkib.userservice.entities

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  val id: Long,

  @Column(length = 32, unique = true)
  var username: String,

  @Column(length = 32, unique = true)
  var email: String,

  @Column(length = 20)
  @JsonProperty(access = READ_ONLY)
  var password: String,

  @ManyToMany(mappedBy = "role", targetEntity = Role::class)
  val roles: MutableCollection<Role>
)
