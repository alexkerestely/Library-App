package com.sd.cache

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication()
@EnableJpaRepositories("com.sd.cache.persistence.interfaces")
class CacheMS

fun main(args: Array<String>) {
    runApplication<CacheMS>(*args)
}
