package com.ttulka.ecommerce.sales.catalog.category

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class UriTest {

    @Test
    fun uri_value() {
        val uri = Uri("test")
        assertThat(uri.value()).isEqualTo("test")
    }

    @Test
    fun uri_fails_for_a_blank_value() {
        assertThrows<IllegalArgumentException> { Uri(" \t\n") }
    }

    @Test
    fun uri_fails_for_invalid_values() {
        assertAll(
                { assertThrows<IllegalArgumentException> { Uri("Test") } },
                { assertThrows<IllegalArgumentException> { Uri("testTest") } },
                { assertThrows<IllegalArgumentException> { Uri("-test") } },
                { assertThrows<IllegalArgumentException> { Uri("0test") } },
                { assertThrows<IllegalArgumentException> { Uri("test-") } },
                { assertThrows<IllegalArgumentException> { Uri(" test") } }
        )
    }

    @Test
    fun uri_accepts_valid_values() {
        assertAll(
                { assertDoesNotThrow { Uri("test") } },
                { assertDoesNotThrow { Uri("test0") } },
                { assertDoesNotThrow { Uri("test-test") } },
                { assertDoesNotThrow { Uri("test-0-test") } },
                { assertDoesNotThrow { Uri("test0test") } },
                { assertDoesNotThrow { Uri("test-0test") } },
                { assertDoesNotThrow { Uri("test0-test") } }
        )
    }
}