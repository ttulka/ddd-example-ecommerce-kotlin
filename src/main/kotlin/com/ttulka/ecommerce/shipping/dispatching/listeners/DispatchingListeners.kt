package com.ttulka.ecommerce.shipping.dispatching.listeners

import com.ttulka.ecommerce.billing.payment.PaymentCollected
import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.shipping.delivery.DeliveryPrepared
import com.ttulka.ecommerce.shipping.dispatching.DispatchingSaga
import com.ttulka.ecommerce.shipping.dispatching.OrderId
import com.ttulka.ecommerce.warehouse.GoodsFetched
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
internal class DispatchingListeners(
        private val saga: DispatchingSaga) {

    @TransactionalEventListener
    @Async
    fun on(event: DeliveryPrepared) {
        saga.prepared(OrderId(event.orderId))
    }

    @TransactionalEventListener
    @Async
    fun on(event: OrderPlaced) {
        saga.accepted(OrderId(event.orderId))
    }

    @TransactionalEventListener
    @Async
    fun on(event: GoodsFetched) {
        saga.fetched(OrderId(event.orderId))
    }

    @TransactionalEventListener
    @Async
    fun on(event: PaymentCollected) {
        saga.paid(OrderId(event.referenceId))
    }
}