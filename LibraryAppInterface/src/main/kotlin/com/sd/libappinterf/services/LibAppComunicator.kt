package com.sd.libappinterf.services

import com.sd.libappinterf.config.RabbitMqConnectionFactoryComponent
import com.sd.libappinterf.interfaces.LibAppCommunicatorInterface
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LibAppComunicator : LibAppCommunicatorInterface {

    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }

    override fun sendMessageToApp(message: String) {
        this.amqpTemplate.convertAndSend(connectionFactory.getExchange(), connectionFactory.getRoutingKey(), message)
    }
}