package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.FindPayments
import com.ttulka.ecommerce.billing.payment.Payments
import com.ttulka.ecommerce.common.events.EventPublisher
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Find Payments use-cases.
 */
internal class FindPaymentsJdbc(
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : FindPayments {

    override fun all(): Payments =
        PaymentsJdbc(
            "SELECT id, reference_id referenceId, total, status FROM payments",
            jdbcTemplate, eventPublisher)
}