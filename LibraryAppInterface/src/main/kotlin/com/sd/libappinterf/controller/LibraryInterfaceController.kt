package com.sd.libappinterf.controller

import com.sd.libappinterf.config.RabbitMqConnectionFactoryComponent
import com.sd.libappinterf.interfaces.LibAppCommunicatorInterface
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.Message
import org.springframework.integration.core.MessagingTemplate
import org.springframework.integration.message.GenericMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class LibraryInterfaceController {

    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    private lateinit var libAppCommunicator : LibAppCommunicatorInterface

    @Autowired
    private lateinit var messagingTemplate: MessagingTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }


    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String) {

        val message  = "/print?format="+format
        println("interf : " + message)
        libAppCommunicator.sendMessageToApp(message)
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(
        @RequestParam(required = false, name = "author", defaultValue = "") author: String,
        @RequestParam(required = false, name = "title", defaultValue = "") title: String,
        @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String
    ) {

        var message = "/find?"
        if (author != "") {
            message =message +"author="+author
            println("interf : " + message)
            libAppCommunicator.sendMessageToApp(message)
        }
        if (title != "") {
            message =message +"title="+title
            println("interf : " + message)
            libAppCommunicator.sendMessageToApp(message)
        }
        if (publisher != "") {
            message =message +"publisher="+publisher
            println("interf : " + message)
            libAppCommunicator.sendMessageToApp(message)
        }

    }

    @RabbitListener(queues = ["\${libapp.rabbitmq.results}"])
    fun receiveResultFromApp(message:String)
    {
        //parse message to extract and send it to display result - query + result
        println("recv result frm app: " + message)
        val parsedResponse = message.split(';')

        val parsedQuery = parsedResponse[0].split("?")
        val operation = parsedQuery[0]
        val params = parsedQuery[1].split('=')
        val key = params[0]
        val value = params[1]

        displayResult(parsedResponse[1], operation, value, key)

    }

    //@SendTo("/{operation}?{value}={key}")
    @RequestMapping("/results", method = [RequestMethod.GET])
    @ResponseBody
    fun displayResult(body: String, operation:String, value :String, key :String ):String {

        /*
        val destination = "$operation?$key=$value"
        val msg: Message<String> = GenericMessage(body)
        messagingTemplate.send(destination, msg)

         */
        return body;
    }

}