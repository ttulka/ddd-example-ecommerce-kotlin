package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.Order
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.item.OrderItem

/**
 * Null object implementation for Order entity.
 */
internal class UnknownOrder : Order {

    override fun id(): OrderId = OrderId(0)

    override fun items(): List<OrderItem> = emptyList()

    override fun total(): Money = Money.ZERO
}