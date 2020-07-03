package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.Payment
import com.ttulka.ecommerce.billing.payment.Payment.*
import com.ttulka.ecommerce.billing.payment.PaymentCollected
import com.ttulka.ecommerce.billing.payment.PaymentId
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import java.time.Instant
import java.util.*

/**
 * JDBC implementation of Payment entity.
 */
internal class PaymentJdbc(
        private val id: PaymentId,
        private val referenceId: ReferenceId,
        private val total: Money,
        private var status: Status,
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : Payment {

    enum class Status {
        NEW, REQUESTED, COLLECTED
    }

    private val log = LoggerFactory.getLogger(PaymentJdbc::class.java)

    constructor(referenceId: ReferenceId, total: Money, jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher)
            : this(PaymentId(UUID.randomUUID()), referenceId, total, Status.NEW, jdbcTemplate, eventPublisher)

    override fun id(): PaymentId = id

    override fun referenceId(): ReferenceId = referenceId

    override fun total(): Money = total

    override fun request() {
        if (isRequested || isCollected) {
            throw PaymentAlreadyRequestedException()
        }
        status = Status.REQUESTED

        jdbcTemplate.update(
                "INSERT INTO payments VALUES (?, ?, ?, ?)",
                id.value(), referenceId.value(), total.value(), status.name)

        log.info("Payment requested: {}", this)
    }

    override fun collect() {
        if (isCollected) {
            throw PaymentAlreadyCollectedException()
        }
        if (!isRequested) {
            throw PaymentNotRequestedYetException()
        }
        status = Status.COLLECTED

        jdbcTemplate.update(
                "UPDATE payments SET status = ? WHERE id = ?",
                status.name, id.value())

        eventPublisher.raise(PaymentCollected(Instant.now(), referenceId.value()))

        log.info("Payment collected: {}", this)
    }

    override val isRequested: Boolean
        get() = Status.REQUESTED == status || Status.COLLECTED == status

    override val isCollected: Boolean
        get() = Status.COLLECTED == status

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as PaymentJdbc).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "PaymentJdbc($id, $total, reference=$referenceId)"
}
