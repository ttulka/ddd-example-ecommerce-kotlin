package com.ttulka.ecommerce.shipping.delivery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PlaceTest {

    @Test
    fun place_value() {
        val place = Place("test")
        assertThat(place.value()).isEqualTo("test")
    }

    @Test
    fun place_value_is_trimmed() {
        val place = Place("   test   ")
        assertThat(place.value()).isEqualTo("test")
    }

    @Test
    fun place_fails_for_an_empty_string() {
        assertThrows<IllegalArgumentException> { Place("") }
    }
}