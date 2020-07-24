package com.lorenzoog.gitkib.userservice.services

import com.lorenzoog.gitkib.userservice.exceptions.EntityNotFoundException
import org.springframework.data.domain.Page

/**
 * Class that provide an entity [T].
 */
interface EntityProvider<T> {

  /**
   * Return paginated entities(and probably cached).
   *
   * @return the page with the entities found in page [page] with offset [offset].
   */
  suspend fun findAll(page: Int, offset: Int): Page<T>

  /**
   * Return the entity by its id(and probably cached).
   *
   * @return the entity found with id [id]
   * @throws EntityNotFoundException if couldn't find the entity with id [id]
   */
  @Throws(EntityNotFoundException::class)
  suspend fun findById(id: Long): T

  /**
   * Persist the entity [entityBuilder] in database(and probably cache then)
   *
   * @return the persisted entity
   */
  suspend fun save(entityBuilder: T.() -> Unit): T

  /**
   * Deletes a persisted entity(and probably from the cache also)
   *
   * @return [Unit]
   */
  suspend fun deleteById(id: Long)

}
