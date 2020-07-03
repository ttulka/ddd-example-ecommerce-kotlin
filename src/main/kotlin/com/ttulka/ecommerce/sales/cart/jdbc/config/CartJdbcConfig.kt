package com.ttulka.ecommerce.sales.cart.jdbc.config

import com.ttulka.ecommerce.sales.cart.jdbc.RetrieveCartJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for JDBC implementation for Cart service.
 */
@Configuration
internal class CartJdbcConfig {

    @Bean
    fun retrieveCartJdbc(jdbcTemplate: JdbcTemplate) = RetrieveCartJdbc(jdbcTemplate)
}
