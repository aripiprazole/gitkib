package com.lorenzoog.gitkib.userservice.services

import org.springframework.data.domain.Page
import org.springframework.data.rest.webmvc.ResourceNotFoundException

/**
 * Class that provide an entity [T].
 */
interface EntityProvider<T> {

  /**
   * Return paginated entities(and probably cached).
   *
   * @return the page with the entities found in page [page] with offset [offset].
   */
  fun findAll(page: Int, offset: Int): Page<T>

  /**
   * Return the entity by its id(and probably cached).
   *
   * @return the entity found with id [id]
   * @throws ResourceNotFoundException if couldn't find the entity with id [id]
   */
  @Throws(ResourceNotFoundException::class)
  fun findById(id: Long): T

  /**
   * Persist the entity [entity] in database(and probably cache then)
   *
   * @return the persisted entity
   */
  fun save(entity: T): T

  /**
   * Deletes a persisted entity(and probably from the cache also)
   *
   * @return [Unit]
   */
  fun deleteById(id: Long)

}
