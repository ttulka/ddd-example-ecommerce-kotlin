package com.ttulka.ecommerce.shipping.delivery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DeliveryIdTest {

    @Test
    fun string_id_value() {
        val orderId = OrderId(123L)
        assertThat(orderId.value()).isEqualTo("123")
    }

    @Test
    fun fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { OrderId(" \t\n") }
    }
}