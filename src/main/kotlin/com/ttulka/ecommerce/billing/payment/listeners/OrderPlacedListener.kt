package com.ttulka.ecommerce.billing.payment.listeners

import com.ttulka.ecommerce.billing.payment.CollectPayment
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.OrderPlaced
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

/**
 * Payment listener for OrderPlaced event.
 */
@Component("payment-orderPlacedListener") // a custom name to avoid collision
internal class OrderPlacedListener(
        private val collectPayment: CollectPayment) {

    @TransactionalEventListener
    @Async
    fun on(event: OrderPlaced) {
        collectPayment.collect(
                ReferenceId(event.orderId),
                Money(event.total))
    }
}