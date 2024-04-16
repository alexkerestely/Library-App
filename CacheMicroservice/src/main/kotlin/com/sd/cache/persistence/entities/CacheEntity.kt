package com.sd.cache.persistence.entities

import javax.persistence.*

@Entity
@Table(name="cache")
class CacheEntity {

    constructor()
    constructor(timestamp:String, query:String, result:String):this() {
        this.timestamp = timestamp
        this.query = query
        this.result = result
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id:Int =0

    private var timestamp:String = ""
    private var query:String = ""
    private var result:String = ""

    fun getId() :Int { return id}
    fun setId(id:Int) { this.id = id }

    fun getTimestamp() :String { return timestamp}
    fun setTimestamp(tmp:String) { this.timestamp = tmp }

    fun getQuery() :String { return query}
    fun setQuery(query : String) { this.query = query }

    fun getResult() :String { return result}
    fun setResult(result : String) { this.result = result }

    override fun toString(): String {
        return "CacheEntity(id=$id, timestamp='$timestamp', query='$query', result='$result')"
    }


}