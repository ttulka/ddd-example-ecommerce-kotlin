package com.ttulka.ecommerce.warehouse.listeners

import com.ttulka.ecommerce.shipping.delivery.DeliveryDispatched
import com.ttulka.ecommerce.warehouse.OrderId
import com.ttulka.ecommerce.warehouse.RemoveFetchedGoods
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

/**
 * Warehouse listener for DeliveryDispatched event.
 */
@Component("warehouse-deliveryDispatchedListener") // a custom name to avoid collision
internal class DeliveryDispatchedListener(
        private val removeFetchedGoods: RemoveFetchedGoods) {

    @TransactionalEventListener
    @Async
    fun on(event: DeliveryDispatched) {
        removeFetchedGoods.removeForOrder(OrderId(event.orderId))
    }
}