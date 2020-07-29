package com.lorenzoog.gitkib.userservice.tests

import com.lorenzoog.gitkib.userservice.Application
import com.lorenzoog.gitkib.userservice.services.DatabaseService
import com.lorenzoog.gitkib.userservice.setupDefaultBeans
import com.nhaarman.mockitokotlin2.mock
import org.springframework.context.support.BeanDefinitionDsl

@Suppress("NOTHING_TO_INLINE")
inline fun createApplication(crossinline setupBeans: BeanDefinitionDsl.() -> Unit = {}) =
  Application {
    setupDefaultBeans()
    setupBeans()

    bean<DatabaseService>(isPrimary = true) { mock() }
  }
