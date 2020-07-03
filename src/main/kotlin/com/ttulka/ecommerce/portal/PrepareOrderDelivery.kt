package com.ttulka.ecommerce.portal

import com.ttulka.ecommerce.shipping.delivery.Address
import com.ttulka.ecommerce.shipping.delivery.OrderId
import com.ttulka.ecommerce.shipping.delivery.PrepareDelivery
import java.util.*

/**
 * Prepare Delivery for Order use-case.
 */
internal class PrepareOrderDelivery(private val prepareDelivery: PrepareDelivery) {

    /**
     * Prepare a new delivery for the order.
     *
     * @param orderId the order ID
     * @param address the delivery address
     */
    fun prepareDelivery(orderId: UUID, address: Address) {
        // here a command message PrepareDelivery could be sent for lower coupling
        prepareDelivery.prepare(OrderId(orderId), address)
    }
}
