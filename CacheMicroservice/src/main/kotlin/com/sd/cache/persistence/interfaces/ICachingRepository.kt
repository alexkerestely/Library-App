package com.sd.cache.persistence.interfaces

import com.sd.cache.persistence.entities.CacheEntity

interface ICachingRepository  {

    fun getByQuery(query:String) : CacheEntity?
    fun add(cacheEntity: CacheEntity)
    fun updateCacheEntry(result: CacheEntity)


    fun showAllRecords()
}