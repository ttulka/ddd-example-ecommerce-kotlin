package com.ttulka.ecommerce.shipping.delivery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class DeliveryInfoTest {

    @Test
    fun delivery_info_values() {
        val deliveryInfo = DeliveryInfo(DeliveryId(123), OrderId(456))
        assertAll(
                { assertThat(deliveryInfo.id()).isEqualTo(DeliveryId(123)) },
                { assertThat(deliveryInfo.orderId()).isEqualTo(OrderId(456)) }
        )
    }
}
