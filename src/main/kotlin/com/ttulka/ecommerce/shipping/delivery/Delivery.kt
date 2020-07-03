package com.ttulka.ecommerce.shipping.delivery

/**
 * Delivery entity.
 */
interface Delivery {

    fun id(): DeliveryId

    fun orderId(): OrderId

    fun address(): Address

    /**
     * @throws [DeliveryAlreadyPreparedException] when already prepared.
     */
    fun prepare()

    /**
     * @throws [DeliveryAlreadyDispatchedException] when already dispatched.
     */
    fun dispatch()

    val isDispatched: Boolean

    class DeliveryAlreadyPreparedException internal constructor() : IllegalStateException()

    class DeliveryAlreadyDispatchedException internal constructor() : IllegalStateException()
}