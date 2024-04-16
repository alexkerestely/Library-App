package com.sd.cache.business.interfaces

import com.sd.cache.business.models.CacheModel

interface ICachingService {

    fun exists(query:String) : CacheModel?
    fun addToCache(query:String, result:String)
}