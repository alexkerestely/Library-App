package com.sd.cache.persistence.interfaces

import com.sd.cache.persistence.entities.CacheEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IJpaRepository : JpaRepository<CacheEntity, Long> {
    //this one is custom defined
   fun findByQuery(query:String) : CacheEntity?
}