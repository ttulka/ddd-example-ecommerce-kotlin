package com.ttulka.ecommerce.sales.catalog.product

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DescriptionTest {

    @Test
    fun description_value() {
        val description = Description("test")
        assertThat(description.value()).isEqualTo("test")
    }

    @Test
    fun description_value_is_trimmed() {
        val description = Description("   test   ")
        assertThat(description.value()).isEqualTo("test")
    }
}