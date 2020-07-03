package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.Order.OrderHasNoItemsException
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.sales.order.PlaceableOrder
import com.ttulka.ecommerce.sales.order.PlaceableOrder.OrderAlreadyPlacedException
import com.ttulka.ecommerce.sales.order.item.OrderItem
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import java.time.Instant

/**
 * JDBC implementation for Order entity.
 */
internal class OrderJdbc(
        private val id: OrderId,
        private val total: Money,
        private val items: Collection<OrderItem>,
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : PlaceableOrder {

    private val log = LoggerFactory.getLogger(OrderJdbc::class.java)

    private var placed = false

    init {
        if (items.isEmpty()) {
            throw OrderHasNoItemsException()
        }
    }

    override fun id(): OrderId = id

    override fun items(): List<OrderItem> = items.toList()

    override fun total(): Money = total

    override fun place() {
        if (placed) {
            throw OrderAlreadyPlacedException()
        }
        jdbcTemplate.update("INSERT INTO orders VALUES (?, ?)", id.value(), total.value())
        items.forEach {
            jdbcTemplate.update(
                "INSERT INTO order_items VALUES (?, ?, ?)",
                it.productId().value(), it.quantity().value(), id.value())
        }
        placed = true

        eventPublisher.raise(toOrderPlaced())

        log.info("Order placed: {}", this)
    }

    private fun toOrderPlaced(): OrderPlaced =
        OrderPlaced(
            Instant.now(),
            id.value(),
            items.groupingBy { it.productId().value() }
                .aggregateTo(mutableMapOf()) { _, acc: Int?, item, _ -> acc
                    ?.let { acc + item.quantity().value() }
                    ?: item.quantity().value()
                },
            total.value())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as OrderJdbc).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "OrderJdbc($id, $total, $items)"
}