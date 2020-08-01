package com.lorenzoog.gitkib.userservice.tests.factories

interface Factory<T> {

  fun createMany(amount: Int): Set<T>

  fun createOne(builder: T.() -> Unit = {}): T

}
