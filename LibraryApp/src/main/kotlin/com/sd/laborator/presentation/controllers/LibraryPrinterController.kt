package com.sd.laborator.presentation.controllers

import com.sd.laborator.business.interfaces.CommunicatorInterface
import com.sd.laborator.business.interfaces.ILibraryDAOService
import com.sd.laborator.business.interfaces.ILibraryPrinterService
import com.sd.laborator.presentation.config.RabbitMqConnectionFactoryComponent
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Controller

@Controller
class LibraryPrinterController {
    @Autowired
    private lateinit var _libraryDAOService: ILibraryDAOService

    @Autowired
    private lateinit var _libraryPrinterService: ILibraryPrinterService

    @Autowired
    private lateinit var communicatorService : CommunicatorInterface

    @Autowired
    private lateinit var connectionFactory: RabbitMqConnectionFactoryComponent

    private lateinit var amqpTemplate: AmqpTemplate

    @Autowired
    fun initTemplate() {
        this.amqpTemplate = connectionFactory.rabbitTemplate()
    }


    @RabbitListener(queues = ["\${libapp.rabbitmq.app_data}"])
    fun processDataRequest(data:String) {
        //app received a req to send some data - query
        println("app data recv $data")
        //perform operation
        val parsedQuery = data.split("?")
        val operation = parsedQuery[0]
        val params = parsedQuery[1].split('=')
        val key = params[0]
        val value = params[1]

        var result : String = ""

        when(operation) {
            "/print" -> {
                when(value) {
                    "html" -> result = _libraryPrinterService.printHTML(_libraryDAOService.getBooks())
                    "json" -> result = _libraryPrinterService.printJSON(_libraryDAOService.getBooks())
                    "raw" -> result = _libraryPrinterService.printRaw(_libraryDAOService.getBooks())
                }

            }
            "/find" -> {
                when(key) {
                    "author" -> result = this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByAuthor(value))
                    "title" -> result = this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByTitle(value))
                    "publisher" -> result = this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByPublisher(value))
                }
            }
        }

        println("result is $result")
        //send to cache
        communicatorService.sendResultToCache("$data;$result")

        //send to interface
        communicatorService.sendResultToInterface("$data;$result")
    }


    @RabbitListener(queues = ["\${libapp.rabbitmq.interface_requests}", "\${libapp.rabbitmq.app_queries}"])
    fun processRequest(data : String, @Header(AmqpHeaders.CONSUMER_QUEUE) queue : String)
    //fun receiveRequestFromInterface(request: String){
    {

        //communicatorService.sendQueryToCache(data)
        ///println("interf req received : " + data)
        when(queue) {
            "libapp.interface_requests" -> {
                //app received a query from the user - message obj
                println("interf req received : " + data)
                communicatorService.sendQueryToCache(data)
            }
            "libapp.app_queries" ->  {
                //app received a result from cache - data

                //send to interface
                println("app queries received : " + data)
                communicatorService.sendResultToInterface(data)

            }


        }

    }

}