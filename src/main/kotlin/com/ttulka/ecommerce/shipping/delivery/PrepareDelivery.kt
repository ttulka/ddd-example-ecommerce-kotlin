package com.ttulka.ecommerce.shipping.delivery

/**
 * Prepare Delivery use-case.
 */
interface PrepareDelivery {

    /**
     * Prepares a new delivery.
     *
     * @param orderId the order ID
     * @param address the delivery address
     * @throws [Delivery.DeliveryAlreadyPreparedException] when already prepared.
     */
    fun prepare(orderId: OrderId, address: Address)
}
