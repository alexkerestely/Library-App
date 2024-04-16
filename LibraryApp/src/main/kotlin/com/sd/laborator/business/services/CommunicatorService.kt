package com.sd.laborator.business.services

import com.sd.laborator.business.interfaces.CommunicatorInterface
import com.sd.laborator.presentation.config.RabbitMqConnectionFactoryComponent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommunicatorService : CommunicatorInterface {


    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    override fun sendQueryToCache(query: String) {
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange("cache_forQueries"),
            connectionFactory.getRoutingKey("cache_forQueries"), query)
    }

    override fun sendResultToInterface(data: String) {
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange("interface"),
            connectionFactory.getRoutingKey("interface"), data)
    }

    override fun sendResultToCache(result : String) {
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange("cache_forData"),
            connectionFactory.getRoutingKey("cache_forData"), result)
    }
}