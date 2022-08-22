package me.devgabi.gitkib.userservice.utils

import org.kodein.di.LazyDI
import org.kodein.di.instance

inline fun <reified T : Any> LazyDI.value(tag: Any? = null): T {
  val value by instance<T>()

  return value
}
