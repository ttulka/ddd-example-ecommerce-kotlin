package com.ttulka.ecommerce.billing.payment.jdbc.config

import com.ttulka.ecommerce.billing.payment.jdbc.CollectPaymentJdbc
import com.ttulka.ecommerce.billing.payment.jdbc.FindPaymentsJdbc
import com.ttulka.ecommerce.common.events.EventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for JDBC implementation for Payment service.
 */
@Configuration
internal class PaymentJdbcConfig {
    
    @Bean
    fun findPaymentsJdbc(jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher) =
        FindPaymentsJdbc(jdbcTemplate, eventPublisher)

    @Bean
    fun collectPaymentJdbc(jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher): CollectPaymentJdbc =
        CollectPaymentJdbc(jdbcTemplate, eventPublisher)
}