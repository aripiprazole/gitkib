package com.lorenzoog.gitkib.commons.utils

/**
 * Filter when the value is not null
 *
 * @return [Unit]
 */
inline fun <T> T?.whenNotNull(callback: (T) -> Unit) {
  if(this != null) {
    callback(this)
  }
}
