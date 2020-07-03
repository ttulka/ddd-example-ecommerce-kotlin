package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.shipping.delivery.*

/**
 * Null object implementation for Delivery entity.
 */
internal class UnknownDelivery : Delivery {

    override fun id(): DeliveryId = DeliveryId(0)

    override fun orderId(): OrderId = OrderId(0)

    override fun address(): Address =
        Address(
            Person("Unknown Person"),
            Place("Unknown"))

    override fun prepare() {
        // do nothing
    }

    override fun dispatch() {
        // do nothing
    }

    override val isDispatched: Boolean
        get() = false
}