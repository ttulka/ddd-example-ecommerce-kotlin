package com.ttulka.ecommerce.common.primitives

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MoneyTest {

    @Test
    fun money_value() {
        val money = Money(12.34f)
        assertThat(money.value()).isEqualTo(12.34f)
    }

    @Test
    fun money_created_for_a_zero_value() {
        val money = Money(0f)
        assertThat(money.value()).isEqualTo(0f)
    }

    @Test
    fun zero_money_has_a_zero_value() {
        assertThat(Money.ZERO.value()).isEqualTo(0f)
    }

    @Test
    fun money_fails_for_a_negative_value() {
        assertThrows<IllegalArgumentException> { Money(-12.34f) }
    }

    @Test
    fun money_fails_for_a_value_greater_than_one_million() {
        assertThrows<IllegalArgumentException> { Money(1000000.1f) }
    }

    @Test
    fun money_is_multiplied() {
        val money = Money(12.34f)
        assertThat(money * 2).isEqualTo(Money(12.34f * 2))
    }

    @Test
    fun money_is_added() {
        val money = Money(12.34f)
        assertThat(money + Money(2.5f)).isEqualTo(Money(12.34f + 2.5f))
    }
}