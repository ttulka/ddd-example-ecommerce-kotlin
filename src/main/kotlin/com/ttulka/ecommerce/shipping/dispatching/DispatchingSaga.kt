package com.ttulka.ecommerce.shipping.dispatching

/**
 * Dispatching Saga. Integration service for dispatching Delivery.
 * <p>
 * A saga is a long-running process with a state.
 * <p>
 * A saga handles multiple messages and holds its private state.
 */
interface DispatchingSaga {

    fun prepared(orderId: OrderId)

    fun accepted(orderId: OrderId)

    fun fetched(orderId: OrderId)

    fun paid(orderId: OrderId)
}