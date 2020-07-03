package com.ttulka.ecommerce.sales.catalog.category

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoryIdTest {

    @Test
    fun string_id_value() {
        val categoryId = CategoryId(123L)
        Assertions.assertThat(categoryId.value()).isEqualTo("123")
    }

    @Test
    fun fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { CategoryId(" \t\n") }
    }
}