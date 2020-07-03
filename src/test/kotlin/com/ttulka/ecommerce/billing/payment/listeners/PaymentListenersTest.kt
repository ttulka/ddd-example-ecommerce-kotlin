package com.ttulka.ecommerce.billing.payment.listeners

import com.ttulka.ecommerce.billing.payment.CollectPayment
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.order.OrderPlaced
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant

@ExtendWith(MockitoExtension::class)
internal class PaymentListenersTest {

    @Test
    fun on_order_placed_collects_a_payment() {
        val collectPayment = mock(CollectPayment::class.java)
        val listener = OrderPlacedListener(collectPayment)

        listener.on(OrderPlaced(Instant.now(), "TEST123", emptyMap(), 123.5f))

        verify(collectPayment).collect(ReferenceId("TEST123"), Money(123.5f))
    }
}
