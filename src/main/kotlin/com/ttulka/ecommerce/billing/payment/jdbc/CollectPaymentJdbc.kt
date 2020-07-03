package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.CollectPayment
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * JDBC implementation for Collect Payment use-cases.
 */
internal open class CollectPaymentJdbc(
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : CollectPayment {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun collect(referenceId: ReferenceId, total: Money) {
        val payment = PaymentJdbc(referenceId, total, jdbcTemplate, eventPublisher)
        payment.request()

        // here an external service like PayPal or Visa is called...
        payment.collect()
    }
}
