package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.Payment
import com.ttulka.ecommerce.billing.payment.PaymentId
import com.ttulka.ecommerce.billing.payment.Payments
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.Enum
import java.math.BigDecimal

/**
 * JDBC implementation of Payments collection.
 */
internal class PaymentsJdbc(
        private val query: String,
        private val queryParams: Array<Any>,
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : Payments {

    companion object {
        private const val UNLIMITED = 1000
    }

    private var start = 0
    private var limit = UNLIMITED

    constructor(query: String, jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher)
            : this(query, emptyArray(), jdbcTemplate, eventPublisher)

    override fun range(start: Int, limit: Int): Payments {
        require(!(start < 0 || limit <= 0 || limit - start > UNLIMITED)) {
            "Start must be greater than zero, " +
                    "items count must be greater than zero and less or equal than " + UNLIMITED
        }
        this.start = start
        this.limit = limit

        return this
    }

    override fun range(limit: Int): Payments = range(0, limit)

    private fun paymentsFromDb(): Iterable<Payment> {
        val params = queryParams.toMutableList()
        params.add(start)
        params.add(limit)
        return jdbcTemplate.queryForList("$query ORDER BY 1 LIMIT ?,?", *params.toTypedArray())
                .map(::toPayment)
    }

    private fun toPayment(entry: Map<String, Any>): Payment {
        return PaymentJdbc(
                PaymentId(entry["id"]!!),
                ReferenceId(entry["referenceId"]!!),
                Money((entry["total"] as BigDecimal).toFloat()),
                Enum.valueOf(PaymentJdbc.Status::class.java, entry["status"] as String),
                jdbcTemplate, eventPublisher)
    }

    override fun iterator(): Iterator<Payment> = paymentsFromDb().iterator()
}