package com.lorenzoog.gitkib.userservice.controllers

import com.lorenzoog.gitkib.userservice.dtos.Page
import com.lorenzoog.gitkib.userservice.dtos.UserCreateDto
import com.lorenzoog.gitkib.userservice.dtos.UserUpdateDto
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserService
import com.lorenzoog.gitkib.userservice.utils.validateAndThrow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.reactive.function.server.*
import java.net.URI

const val USER_CONTROLLER_PAGE_SIZE = 15

fun CoRouterFunctionDsl.userController(userService: UserService) {
  "users".nest {
    GET("/") { request ->
      val page = request.queryParam("page").orElse("0").toInt()

      ok()
        .body<Page<User>>(
          userService.findAll(page, USER_CONTROLLER_PAGE_SIZE)
        )
        .awaitSingle()
    }

    GET("/{id}") { request ->
      val id = request.pathVariable("id").toLong()

      ok()
        .body<User>(userService.findById(id))
        .awaitSingle()
    }

    POST("/") { request ->
      val body = request.awaitBody<UserCreateDto>()
        .apply { validator.validateAndThrow(this) }

      val user = userService.save {
        username = body.username!!
        email = body.email!!
        password = body.password!!
      }

      created(URI("/users/${user.id}"))
        .bodyAndAwait(flowOf(user))
    }

    PUT("/{id}") { request ->
      val id = request.pathVariable("id").toLong()
      val body = request.awaitBody<UserUpdateDto>()
        .apply { validator.validateAndThrow(this) }

      ok()
        .body<User>(userService.updateById(id) {
          body.username?.let { username = it }
          body.email?.let { email = it }
          body.password?.let { password = it }
        })
        .awaitSingle()
    }

    DELETE("/{id}") { request ->
      val id = request.pathVariable("id").toLong()

      userService.deleteById(id)

      noContent().buildAndAwait()
    }
  }
}
