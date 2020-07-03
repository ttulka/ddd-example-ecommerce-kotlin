package com.ttulka.ecommerce.sales.order

/**
 * Find Orders use-case.
 */
interface FindOrders {

    /**
     * Finds an order by the order ID.
     *
     * @param id the order ID
     * @return the order
     */
    fun byId(id: OrderId): Order
}
