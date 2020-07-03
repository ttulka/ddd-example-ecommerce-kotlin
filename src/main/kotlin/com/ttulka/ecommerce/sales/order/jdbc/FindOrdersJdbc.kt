package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.order.FindOrders
import com.ttulka.ecommerce.sales.order.Order
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.item.OrderItem
import com.ttulka.ecommerce.sales.order.item.ProductId
import org.springframework.jdbc.core.JdbcTemplate
import java.math.BigDecimal

/**
 * JDBC implementation for Find Orders use-cases.
 */
internal class FindOrdersJdbc(
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : FindOrders {

    override fun byId(id: OrderId): Order =
        jdbcTemplate.queryForList(
            "SELECT id, total FROM orders WHERE id = ?",
            id.value()).firstOrNull()
            ?.let { toOrder(it, itemsByOrderId(id)) }
            ?: UnknownOrder()

    private fun itemsByOrderId(id: OrderId): List<OrderItem> =
        jdbcTemplate.queryForList(
            "SELECT product_id, quantity FROM order_items WHERE order_id = ?",
            id.value())
            .map(::toOrderItem)

    private fun toOrder(order: Map<String, Any>, items: List<OrderItem>): Order =
        OrderJdbc(
            OrderId(order["id"]!!),
            Money((order["total"] as BigDecimal).toFloat()),
            items,
            jdbcTemplate,
            eventPublisher)

    private fun toOrderItem(item: Map<String, Any>): OrderItem =
        OrderItem(
            ProductId(item["product_id"]!!),
            Quantity(item["quantity"] as Int))
}