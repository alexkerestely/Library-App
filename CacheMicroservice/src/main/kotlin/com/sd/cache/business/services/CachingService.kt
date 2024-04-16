package com.sd.cache.business.services

import com.sd.cache.business.interfaces.ICachingService
import com.sd.cache.business.interfaces.ITimeService
import com.sd.cache.business.models.CacheModel
import com.sd.cache.persistence.entities.CacheEntity
import com.sd.cache.persistence.interfaces.ICachingRepository
import com.sd.cache.persistence.repositories.CachingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureOrder
import org.springframework.stereotype.Service


@Service
class CachingService : ICachingService {



    //private var cachingRepository : ICachingRepository = CachingRepository()
    @Autowired
    private lateinit var cachingRepository : ICachingRepository
    @Autowired
    private lateinit var timeService : ITimeService


    override fun exists(query: String): CacheModel? {

        println("im here")
        //search for the entry in the repository - get persistence object
        val result : CacheEntity? = cachingRepository.getByQuery(query)
        //val unmodResult = result

        if (result != null) {

            //transform it into model
            var cacheModel : CacheModel = CacheModel("", "")
            cacheModel = cacheModel.fromEntity(result)

            //update the stamp

            cachingRepository.updateCacheEntry(result)
            return cacheModel

        }
        else return null
    }

    override fun addToCache(query: String, result: String) {


        //create a persistence object / entity from the data
        val cacheEntity : CacheEntity = CacheEntity("", "", "")
        cacheEntity.setResult(result)
        cacheEntity.setQuery(query)
        cacheEntity.setTimestamp(timeService.getCurrentTime())
        cachingRepository.add(cacheEntity)
    }


}