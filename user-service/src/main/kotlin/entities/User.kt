package com.lorenzoog.gitkib.userservice.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY
import javax.persistence.*
import javax.persistence.FetchType.EAGER
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "users")
data class User(
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  val id: Long,

  @Column(length = 32, unique = true, columnDefinition = "text")
  var username: String,

  @Column(length = 32, unique = true, columnDefinition = "text")
  var email: String,

  @Column(length = 24, columnDefinition = "text")
  @JsonProperty(access = READ_ONLY)
  @JsonIgnore
  var password: String,

  @ManyToMany(fetch = LAZY)
  @JoinTable(
    name = "user_role",
    joinColumns = [JoinColumn(name = "user_id")],
    inverseJoinColumns = [JoinColumn(name = "role_id")]
  )
  val roles: MutableSet<Role>,

  @OneToOne(fetch = EAGER)
  @JoinTable(
    name = "profiles",
    joinColumns = [
      JoinColumn(name = "user_id", referencedColumnName = "id")
    ]
  )
  var profile: Profile? = null
)
