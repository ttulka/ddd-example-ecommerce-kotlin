package com.ttulka.ecommerce.warehouse.listeners

import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.shipping.delivery.DeliveryDispatched
import com.ttulka.ecommerce.warehouse.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class WarehouseListenersTest {

    @Test
    fun on_order_placed_fetches_goods() {
        val fetchGoods = mock(FetchGoods::class.java)
        val listener = OrderPlacedListener(fetchGoods)
        listener.on(OrderPlaced(Instant.now(), "TEST123", mapOf("test-1" to 2), 246f))

        verify(fetchGoods).fetchFromOrder(
                OrderId("TEST123"),
                listOf(ToFetch(ProductId("test-1"), Amount(2))))
    }

    @Test
    fun on_delivery_dispatched_removes_fetched_goods() {
        val removeFetchedGoods = mock(RemoveFetchedGoods::class.java)
        val listener = DeliveryDispatchedListener(removeFetchedGoods)
        listener.on(DeliveryDispatched(Instant.now(), "TEST123"))

        verify(removeFetchedGoods).removeForOrder(OrderId("TEST123"))
    }
}