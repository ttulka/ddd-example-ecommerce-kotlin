package com.ttulka.ecommerce.sales.order.jdbc.config

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.sales.order.jdbc.FindOrdersJdbc
import com.ttulka.ecommerce.sales.order.jdbc.PlaceOrderJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for JDBC implementation for Order service.
 */
@Configuration
internal class OrderJdbcConfig {

    @Bean
    fun findOrdersJdbc(jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher) =
        FindOrdersJdbc(jdbcTemplate, eventPublisher)

    @Bean
    fun placeOrderJdbc(jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher) =
        PlaceOrderJdbc(jdbcTemplate, eventPublisher)
}
