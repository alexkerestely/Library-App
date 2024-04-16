package com.sd.laborator.business.interfaces

interface CommunicatorInterface {
    fun sendQueryToCache(query:String)
    fun sendResultToInterface(data: String)
    fun sendResultToCache(result : String)

}