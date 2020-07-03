package com.ttulka.ecommerce.warehouse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderIdTest {

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