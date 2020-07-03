package com.ttulka.ecommerce.warehouse.listeners

import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.warehouse.*
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

/**
 * Warehouse listener for OrderPlaced event.
 */
@Component("warehouse-orderPlacedListener") // a custom name to avoid collision
internal class OrderPlacedListener(
        private val fetchGoods: FetchGoods) {

    @TransactionalEventListener
    @Async
    fun on(event: OrderPlaced) {
        fetchGoods.fetchFromOrder(
            OrderId(event.orderId),
            event.items.entries
                .map { ToFetch(ProductId(it.key), Amount(it.value)) })
    }
}