package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.Address
import com.ttulka.ecommerce.shipping.delivery.Delivery
import com.ttulka.ecommerce.shipping.delivery.OrderId
import com.ttulka.ecommerce.shipping.delivery.PrepareDelivery
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

/**
 * JDBC implementation for Prepare Delivery use-cases.
 */
internal open class PrepareDeliveryJdbc(
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : PrepareDelivery {

    @Transactional
    override fun prepare(orderId: OrderId, address: Address) {
        val delivery: Delivery = DeliveryJdbc(orderId, address, jdbcTemplate, eventPublisher)
        delivery.prepare()
    }
}
