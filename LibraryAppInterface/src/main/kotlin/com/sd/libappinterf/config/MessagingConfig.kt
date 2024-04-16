package com.sd.libappinterf.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.MessageChannel
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessagingTemplate

@Configuration
class MessagingConfig {

    @Bean
    fun messagingTemplate(): MessagingTemplate {
        return MessagingTemplate()
    }



}