package com.ttulka.ecommerce.sales.catalog.product

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductIdTest {

    @Test
    fun string_id_value() {
        val productId = ProductId(123L)
        Assertions.assertThat(productId.value()).isEqualTo("123")
    }

    @Test
    fun fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { ProductId(" \t\n") }
    }
}