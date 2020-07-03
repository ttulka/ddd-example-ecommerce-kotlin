package com.ttulka.ecommerce.shipping.dispatching.jdbc.config

import com.ttulka.ecommerce.shipping.delivery.DispatchDelivery
import com.ttulka.ecommerce.shipping.dispatching.jdbc.DispatchingSagaJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for Dispatching Saga JDBC.
 */
@Configuration
internal class DispatchingSagaJdbcConfig {

    @Bean
    fun dispatchingSagaJdbc(dispatchDelivery: DispatchDelivery, jdbcTemplate: JdbcTemplate) =
        DispatchingSagaJdbc(dispatchDelivery, jdbcTemplate)
}