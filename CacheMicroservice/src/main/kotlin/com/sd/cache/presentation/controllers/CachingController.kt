package com.sd.cache.presentation.controllers

import com.sd.cache.business.interfaces.ICachingService
import com.sd.cache.presentation.config.RabbitMqConnectionFactoryComponent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CachingController {


    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    private lateinit var cachingService: ICachingService

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    @RabbitListener(queues = ["\${libapp.rabbitmq.cache_queries}"])
    fun receiveQueryToSearch(query : String) {
        // /print?format=html
        println("cache query $query")
        val result = cachingService.exists(query)
        if(result!=null) {
            // found the query
            // send the result back to manager as String
            val formattedResult = "$query;${result.getResult()}"
            println("formatted result $formattedResult")
            sendResultToApp(formattedResult)

        }
        else {
            //didn't find the query
            //ask for the result
           // val formattedResult = "$query"
            println("sendinng request $query")
            sendRequestToApp(query)

        }

    }

    @RabbitListener(queues = ["\${libapp.rabbitmq.cache_data}"])
    fun receiveDataToStore(data : String) {
            println("cache data $data")
            val parsedMsg = data.split(";")
            val query = parsedMsg[0]
            val result = parsedMsg[1]
            cachingService.addToCache(query, result)
    }

    fun sendResultToApp(result : String) {

        this.amqpTemplate.convertAndSend(connectionFactory.getExchange("send_result"),
            connectionFactory.getRoutingKey("send_result"), result)
    }

    fun sendRequestToApp(query : String) {

        this.amqpTemplate.convertAndSend(connectionFactory.getExchange("send_query"),
            connectionFactory.getRoutingKey("send_query"), query)
    }

}