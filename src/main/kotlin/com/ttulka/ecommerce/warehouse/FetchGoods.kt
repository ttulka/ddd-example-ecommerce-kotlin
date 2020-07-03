package com.ttulka.ecommerce.warehouse

/**
 * Fetch Goods use-case.
 */
interface FetchGoods {

    /**
     * Fetches goods for an order.
     *
     * @param orderId the order ID
     * @param toFetch the goods to fetch
     */
    fun fetchFromOrder(orderId: OrderId, toFetch: Collection<ToFetch>)
}