package com.sd.cache.presentation.config

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


    @Value("\${libapp.rabbitmq.app_data_routingkey}")
    private lateinit var appDataRoutingKey: String
    @Value("\${libapp.rabbitmq.app_queries_routingkey}")
    private lateinit var appQueryRoutingKey: String

    @Value("\${libapp.rabbitmq.app_data_exchange}")
    private lateinit var appDataExchange: String
    @Value("\${libapp.rabbitmq.app_queries_exchange}")
    private lateinit var appQueryExchange: String

    fun getExchange(purpose : String): String {

           when(purpose) {
               "send_result" -> return this.appQueryExchange
               "send_query" -> return this.appDataExchange
               else -> return ""
           }

    }

    fun getRoutingKey(purpose : String): String {

        when(purpose) {
            "send_result" -> return this.appQueryRoutingKey
            "send_query" -> return this.appDataRoutingKey
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