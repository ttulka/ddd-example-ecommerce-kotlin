package com.ttulka.ecommerce.common.primitives

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QuantityTest {

    @Test
    fun quantity_value() {
        val quantity = Quantity(123)
        assertThat(quantity.value()).isEqualTo(123)
    }

    @Test
    fun zero_quantity_has_a_zero_value() {
        assertThat(Quantity.ZERO.value()).isEqualTo(0)
    }

    @Test
    fun one_quantity_has_a_value_equals_one() {
        assertThat(Quantity.ONE.value()).isEqualTo(1)
    }

    @Test
    fun quantity_fails_for_a_value_less_than_zero() {
        assertThrows<IllegalArgumentException> { Quantity(-1) }
    }

    @Test
    fun quantity_is_added() {
        assertThat(Quantity(1) + Quantity(2)).isEqualTo(Quantity(3))
    }
}