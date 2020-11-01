package com.lorenzoog.gitkib.userservice.tests.factories

import org.jetbrains.exposed.dao.LongEntity

interface IFactory<T : LongEntity> {

  fun createOne(builder: T.() -> Unit = {}): T
  fun createMany(amount: Int = 15, builder: T.() -> Unit = {}): List<T>

}
