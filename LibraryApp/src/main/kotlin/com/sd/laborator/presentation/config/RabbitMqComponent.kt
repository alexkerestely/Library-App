package com.sd.laborator.presentation.config

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RabbitMqConnectionFactoryComponent {
    @Value("\${spring.rabbitmq.host}")
    private lateinit var host: String
    @Value("\${spring.rabbitmq.port}")
    private val port: Int = 0
    @Value("\${spring.rabbitmq.username}")
    private lateinit var username: String
    @Value("\${spring.rabbitmq.password}")
    private lateinit var password: String


    @Value("\${libapp.rabbitmq.cache_queries_routingkey}")
    private lateinit var cacheQueryRoutingKey: String
    @Value("\${libapp.rabbitmq.interface_routingkey}")
    private lateinit var interfaceRoutingKey: String
    @Value("\${libapp.rabbitmq.cache_data_routingkey}")
    private lateinit var cacheDataRoutingKey: String

    @Value("\${libapp.rabbitmq.app_cache_queries_exchange}")
    private lateinit var cacheQueryExchange: String
    @Value("\${libapp.rabbitmq.app_interface_exchange}")
    private lateinit var interfaceExchange: String
    @Value("\${libapp.rabbitmq.app_cache_data_exchange}")
    private lateinit var cacheDataExchange: String



    fun getExchange(toWhom : String): String {

        when (toWhom) {

            "cache_forQueries"-> return this.cacheQueryExchange
            "cache_forData" -> return this.cacheDataExchange
            "interface" -> return this.interfaceExchange
            else -> return ""
        }

    }

    fun getRoutingKey(toWhom : String): String {
        when (toWhom) {

            "cache_forQueries"-> return this.cacheQueryRoutingKey
            "cache_forData" -> return this.cacheDataRoutingKey
            "interface" -> return this.interfaceRoutingKey
            else -> return ""
        }
    }

    @Bean
    private fun connectionFactory(): ConnectionFactory {
        val connectionFactory = CachingConnectionFactory()
        connectionFactory.host = this.host
        connectionFactory.username = this.username
        connectionFactory.setPassword(this.password)
        connectionFactory.port = this.port
        return connectionFactory
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate = RabbitTemplate(connectionFactory())
}