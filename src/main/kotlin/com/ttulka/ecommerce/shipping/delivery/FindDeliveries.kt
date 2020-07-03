package com.ttulka.ecommerce.shipping.delivery

/**
 * Find Deliveries use-case.
 */
interface FindDeliveries {

    /**
     * Finds all deliveries.
     *
     * @return all deliveries
     */
    fun all(): DeliveryInfos

    /**
     * Finds a delivery by the Order ID.
     *
     * @param orderId the order ID
     * @return the delivery
     */
    fun byOrder(orderId: OrderId): Delivery

    /**
     * Checks if a delivery is prepared for the order ID.
     *
     * @param orderId the order ID
     * @return true if a delivery is prepared, otherwise false
     */
    fun isPrepared(orderId: OrderId): Boolean
}
