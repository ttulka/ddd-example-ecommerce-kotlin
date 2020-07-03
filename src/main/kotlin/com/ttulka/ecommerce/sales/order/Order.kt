package com.ttulka.ecommerce.sales.order

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.item.OrderItem

/**
 * Order entity.
 */
interface Order {

    fun id(): OrderId

    fun items(): List<OrderItem>

    fun total(): Money

    /**
     * OrderHasNoItemsException is thrown when the Order has no items.
     */
    class OrderHasNoItemsException internal constructor(): IllegalStateException()
}