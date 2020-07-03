package com.ttulka.ecommerce.billing.payment

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PaymentIdTest {

    @Test
    fun string_id_value() {
        val paymentId = PaymentId(123L)
        Assertions.assertThat(paymentId.value()).isEqualTo("123")
    }

    @Test
    fun fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { PaymentId(" \t\n") }
    }
}