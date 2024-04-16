package com.sd.cache.persistence.repositories

import com.sd.cache.persistence.entities.CacheEntity
import com.sd.cache.persistence.interfaces.ICachingRepository
import com.sd.cache.persistence.interfaces.IJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class CachingRepository() : ICachingRepository {

    @Autowired
    private lateinit var jpaRepository: IJpaRepository

    override fun getByQuery(query: String): CacheEntity? {

        val cacheEntity = jpaRepository.findByQuery(query)
        return cacheEntity
    }

    override fun add(cacheEntity: CacheEntity) {

        jpaRepository.save(cacheEntity)
    }

    override fun updateCacheEntry(result: CacheEntity) {

        jpaRepository.save(result)

    }

    override fun showAllRecords() {
        val records: List<CacheEntity> = jpaRepository.findAll()
        for(record in records) {
            println(record.toString())
        }
    }


}