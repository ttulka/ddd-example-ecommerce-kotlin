package com.ttulka.ecommerce.shipping.dispatching.listeners

import com.ttulka.ecommerce.billing.payment.PaymentCollected
import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.shipping.delivery.DeliveryPrepared
import com.ttulka.ecommerce.shipping.dispatching.DispatchingSaga
import com.ttulka.ecommerce.shipping.dispatching.OrderId
import com.ttulka.ecommerce.warehouse.GoodsFetched
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant

@ExtendWith(MockitoExtension::class)
internal class DispatchingListenersTest {
    @Test
    fun prepared_called() {
        val saga = mock(DispatchingSaga::class.java)
        val listeners = DispatchingListeners(saga)
        listeners.on(DeliveryPrepared(Instant.now(), "TEST123"))

        verify(saga).prepared(OrderId("TEST123"))
    }

    @Test
    fun accepted_called() {
        val saga = mock(DispatchingSaga::class.java)
        val listeners = DispatchingListeners(saga)
        listeners.on(OrderPlaced(Instant.now(), "TEST123", emptyMap(), 1f))

        verify(saga).accepted(OrderId("TEST123"))
    }

    @Test
    fun fetched_called() {
        val saga = mock(DispatchingSaga::class.java)
        val listeners = DispatchingListeners(saga)
        listeners.on(GoodsFetched(Instant.now(), "TEST123"))

        verify(saga).fetched(OrderId("TEST123"))
    }

    @Test
    fun paid_called() {
        val saga = mock(DispatchingSaga::class.java)
        val listeners = DispatchingListeners(saga)
        listeners.on(PaymentCollected(Instant.now(), "TEST123"))

        verify(saga).paid(OrderId("TEST123"))
    }
}
