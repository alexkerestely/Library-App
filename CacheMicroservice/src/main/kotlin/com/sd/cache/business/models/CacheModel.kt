package com.sd.cache.business.models

import com.sd.cache.persistence.entities.CacheEntity

class CacheModel(private var query : String, private var result : String) {

    fun getQuery() : String { return query }

    fun setQuery(query : String) {this.query = query}

    fun getResult() : String { return result }

    fun setResult(result : String) {this.result = result}

    fun toEntity(item:CacheModel) :CacheEntity {
        val cacheEntity : CacheEntity = CacheEntity("", "", "")
        cacheEntity.setId(-1)
        cacheEntity.setQuery(item.getQuery())
        cacheEntity.setResult(item.getResult())
        cacheEntity.setTimestamp("")
        return cacheEntity
    }

    fun fromEntity(item : CacheEntity) :CacheModel {
        val cacheModel : CacheModel = CacheModel("", "")
        cacheModel.query = item.getQuery()
        cacheModel.result= item.getResult()
        return cacheModel
    }


}