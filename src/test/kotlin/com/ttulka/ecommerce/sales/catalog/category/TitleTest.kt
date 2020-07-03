package com.ttulka.ecommerce.sales.catalog.category

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TitleTest {

    @Test
    fun title_value() {
        val title = Title("test")
        assertThat(title.value()).isEqualTo("test")
    }

    @Test
    fun title_value_is_trimmed() {
        val title = Title("   01234567890123456789   ")
        assertThat(title.value()).isEqualTo("01234567890123456789")
    }

    @Test
    fun title_fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { Title(" \t\n") }
    }
}