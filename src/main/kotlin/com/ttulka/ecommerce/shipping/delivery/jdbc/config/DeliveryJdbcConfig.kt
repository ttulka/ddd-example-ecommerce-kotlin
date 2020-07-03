package com.ttulka.ecommerce.shipping.delivery.jdbc.config

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.DispatchDelivery
import com.ttulka.ecommerce.shipping.delivery.FindDeliveries
import com.ttulka.ecommerce.shipping.delivery.jdbc.FindDeliveriesJdbc
import com.ttulka.ecommerce.shipping.delivery.jdbc.PrepareDeliveryJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for JDBC implementation for Delivery service.
 */
@Configuration
internal class DeliveryJdbcConfig {

    @Bean
    fun findDeliveriesJdbc(jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher) =
        FindDeliveriesJdbc(jdbcTemplate, eventPublisher)

    @Bean
    fun prepareDeliveryJdbc(jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher) =
        PrepareDeliveryJdbc(jdbcTemplate, eventPublisher)

    @Bean
    fun dispatchDelivery(findDeliveries: FindDeliveries) = DispatchDelivery(findDeliveries)
}
