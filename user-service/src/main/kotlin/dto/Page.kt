package me.devgabi.gitkib.userservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
  val content: List<T>
) {

  fun <B> map(consumer: (T) -> B) = Page(
    content = content.map(consumer)
  )

}
