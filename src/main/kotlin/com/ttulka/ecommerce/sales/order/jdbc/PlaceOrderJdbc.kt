package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.PlaceOrder
import com.ttulka.ecommerce.sales.order.item.OrderItem
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * JDBC implementation for Place Order use-cases.
 */
internal open class PlaceOrderJdbc(
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : PlaceOrder {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun place(orderId: OrderId, items: Collection<OrderItem>, total: Money) {
        OrderJdbc(orderId, total, items, jdbcTemplate, eventPublisher)
                .place()
    }
}