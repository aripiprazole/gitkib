package com.lorenzoog.gitkib.userservice.dtos

data class Page<T>(
  val content: List<T>,
  val total: Int,
  val currentPage: Int
)
