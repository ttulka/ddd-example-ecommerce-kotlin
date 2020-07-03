package com.ttulka.ecommerce.warehouse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class AmountTest {

    @Test
    fun amount_value() {
        val amount = Amount(123)
        assertThat(amount.value()).isEqualTo(123)
    }

    @Test
    fun amount_fails_for_a_value_less_than_zero() {
        assertThrows<IllegalArgumentException> { Amount(-1) }
    }

    @Test
    fun zero_amount_has_zero_value() {
        assertThat(Amount.ZERO.value()).isEqualTo(0)
    }

    @Test
    fun amount_is_added() {
        val amount = Amount(1) + Amount(2)
        assertThat(amount.value()).isEqualTo(3)
    }

    @Test
    fun amount_is_subtracted() {
        val amount = Amount(3) - Amount(1)
        assertThat(amount.value()).isEqualTo(2)
    }

    @Test
    fun amount_is_compared() {
        val amountGreater = Amount(3)
        val amountLess = Amount(1)
        assertAll(
                { assertThat(amountGreater > amountLess).isTrue() } ,
                { assertThat(amountGreater >= amountLess).isTrue() },
                { assertThat(amountGreater < amountLess).isFalse() },
                { assertThat(amountGreater <= amountLess).isFalse() },
                { assertThat(amountGreater < amountGreater).isFalse() },
                { assertThat(amountGreater > amountGreater).isFalse() },
                { assertThat(amountGreater <= amountGreater).isTrue() },
                { assertThat(amountGreater >= amountGreater).isTrue() }
        )
    }
}