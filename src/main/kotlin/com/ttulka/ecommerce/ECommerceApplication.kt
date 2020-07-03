package com.ttulka.ecommerce

import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import nz.net.ultraq.thymeleaf.LayoutDialect
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync

/**
 * Spring Boot based monolithic application.
 */
@SpringBootApplication
@EnableAsync
class ECommerceApplication {

    @Bean
    fun eventPublisher(applicationEventPublisher: ApplicationEventPublisher): EventPublisher =
        object : EventPublisher {
            override fun raise(event: DomainEvent) = applicationEventPublisher.publishEvent(event)
        }

    @Bean
    fun layoutDialect() = LayoutDialect()
}

fun main(args: Array<String>) {
    runApplication<ECommerceApplication>(*args)
}
