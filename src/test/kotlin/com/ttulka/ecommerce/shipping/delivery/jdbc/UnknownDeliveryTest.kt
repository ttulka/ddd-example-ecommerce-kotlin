package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.shipping.delivery.Delivery
import com.ttulka.ecommerce.shipping.delivery.DeliveryId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class UnknownDeliveryTest {
    @Test
    fun unknown_delivery_has_values() {
        val unknownDelivery: Delivery = UnknownDelivery()
        assertAll(
                { assertThat(unknownDelivery.id()).isEqualTo(DeliveryId(0)) },
                { assertThat(unknownDelivery.orderId()).isNotNull() },
                { assertThat(unknownDelivery.address()).isNotNull() }
        )
    }

    @Test
    fun prepare_noop() {
        val unknownDelivery: Delivery = UnknownDelivery()
        unknownDelivery.prepare()

        assertThat(UnknownDelivery().isDispatched).isFalse()
    }

    @Test
    fun dispatch_noop() {
        val unknownDelivery: Delivery = UnknownDelivery()
        unknownDelivery.dispatch()

        assertThat(UnknownDelivery().isDispatched).isFalse()
    }

    @Test
    fun unknown_delivery_is_not_dispatched() {
        assertThat(UnknownDelivery().isDispatched).isFalse()
    }
}