package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.*
import com.ttulka.ecommerce.shipping.delivery.Delivery.DeliveryAlreadyDispatchedException
import com.ttulka.ecommerce.shipping.delivery.Delivery.DeliveryAlreadyPreparedException
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate
import java.time.Instant
import java.util.*

/**
 * JDBC implementation for Delivery entity.
 */
internal class DeliveryJdbc(
        private val id: DeliveryId,
        private val orderId: OrderId,
        private val address: Address,
        private var dispatched: Boolean,
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : Delivery {

    constructor(orderId: OrderId, address: Address, jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher)
            : this(DeliveryId(UUID.randomUUID()), orderId, address, false, jdbcTemplate, eventPublisher)

    private val log = LoggerFactory.getLogger(DeliveryJdbc::class.java)

    override val isDispatched: Boolean
        get() = dispatched

    override fun id(): DeliveryId = id

    override fun orderId(): OrderId = orderId

    override fun address(): Address = address

    override fun prepare() {
        try {
            jdbcTemplate.update("INSERT INTO deliveries VALUES (?, ?, ?, ?)",
                    id.value(), orderId.value(), address.person().value(), address.place().value())

        } catch (e: DataIntegrityViolationException) {
            throw DeliveryAlreadyPreparedException()
        }
        eventPublisher.raise(DeliveryPrepared(Instant.now(), orderId.value()))

        log.info("Delivery prepared: {}", this)
    }

    override fun dispatch() {
        try {
            jdbcTemplate.update("INSERT INTO dispatched_deliveries VALUES (?)", id.value())

        } catch (e: DataIntegrityViolationException) {
            throw DeliveryAlreadyDispatchedException()
        }
        dispatched = true

        // do the actual dispatching...

        eventPublisher.raise(DeliveryDispatched(Instant.now(), orderId.value()))

        log.info("Delivery dispatched: {}", this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as DeliveryJdbc).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "DeliveryJdbc($id, $address, orderId=$orderId)"
}