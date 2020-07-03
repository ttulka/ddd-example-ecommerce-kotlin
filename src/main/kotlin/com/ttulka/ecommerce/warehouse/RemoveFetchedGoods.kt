package com.ttulka.ecommerce.warehouse

/**
 * Remove Fetched Goods use-case.
 */
interface RemoveFetchedGoods {

    /**
     * Removes all fetched goods for an order.
     *
     * @param orderId the order ID
     */
    fun removeForOrder(orderId: OrderId)
}