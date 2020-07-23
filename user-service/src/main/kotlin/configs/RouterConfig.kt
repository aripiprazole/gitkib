package com.lorenzoog.gitkib.userservice.configs

import com.lorenzoog.gitkib.userservice.controllers.AppProfileController
import com.lorenzoog.gitkib.userservice.controllers.AuthController
import com.lorenzoog.gitkib.userservice.controllers.UserController
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl
import org.springframework.web.reactive.function.server.coRouter

fun setupRoutes(
  userController: UserController,
  profileController: AppProfileController,
  authController: AuthController
) = coRouter {
  contentType(APPLICATION_JSON)
    .and(accept(APPLICATION_JSON))
    .nest {
      setupUserControllerRoutes(userController)
      setupAuthControllerRoutes(authController)
      setupProfileControllerRoutes(profileController)
    }
}

fun CoRouterFunctionDsl.setupProfileControllerRoutes(profileController: AppProfileController) {
  GET(AppProfileController.INDEX_ENDPOINT).invoke(profileController::index)
  GET(AppProfileController.SHOW_ENDPOINT).invoke(profileController::show)
  PUT(AppProfileController.UPDATE_ENDPOINT).invoke(profileController::update)
}

fun CoRouterFunctionDsl.setupAuthControllerRoutes(authController: AuthController) {
  POST(AuthController.AUTHENTICATE_ENDPOINT).invoke(authController::authenticate)
}

fun CoRouterFunctionDsl.setupUserControllerRoutes(userController: UserController) {
  GET(UserController.INDEX_ENDPOINT).invoke(userController::index)
  POST(UserController.STORE_ENDPOINT).invoke(userController::store)
  GET(UserController.SHOW_ENDPOINT).invoke(userController::show)
  PUT(UserController.UPDATE_ENDPOINT).invoke(userController::update)
  DELETE(UserController.DESTROY_ENDPOINT).invoke(userController::destroy)
}
