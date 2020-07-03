package com.ttulka.ecommerce.sales.order

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.item.OrderItem

/**
 * Place Order use-case.
 */
interface PlaceOrder {

    /**
     * Places a new order.
     *
     * @param orderId the order ID
     * @param items   the order items
     */
    fun place(orderId: OrderId, items: Collection<OrderItem>, total: Money)
}
