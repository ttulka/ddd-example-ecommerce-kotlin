package com.ttulka.ecommerce.billing.payment

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReferenceIdTest {

    @Test
    fun string_id_value() {
        val referenceId = ReferenceId(123L)
        Assertions.assertThat(referenceId.value()).isEqualTo("123")
    }

    @Test
    fun fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { ReferenceId(" \t\n") }
    }
}