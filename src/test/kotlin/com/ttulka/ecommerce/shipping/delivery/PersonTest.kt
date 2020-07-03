package com.ttulka.ecommerce.shipping.delivery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class PersonTest {
    @Test
    fun person_value() {
        val person = Person("Test Test")
        assertThat(person.value()).isEqualTo("Test Test")
    }

    @Test
    fun person_fails_for_an_empty_string() {
        assertThrows<IllegalArgumentException> { Person("") }
    }

    @Test
    fun person_fails_for_invalid_values() {
        assertAll(
                { assertThrows<IllegalArgumentException> { Person("test") } },
                { assertThrows<IllegalArgumentException> { Person("test test") } },
                { assertThrows<IllegalArgumentException> { Person("test Test") } },
                { assertThrows<IllegalArgumentException> { Person("Test test") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test!") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test@") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test+") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test'") } },
                { assertThrows<IllegalArgumentException> { Person("Test0 Test") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test0") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test0") } },
                { assertThrows<IllegalArgumentException> { Person("Test0a Test") } },
                { assertThrows<IllegalArgumentException> { Person("Test Test0a") } }
        )
    }

    @Test
    fun person_accepts_valid_values() {
        assertAll(
                { assertDoesNotThrow { Person("Alan Turing") } },
                { assertDoesNotThrow { Person("John von Neumann") } },
                { assertDoesNotThrow { Person("Old McDonald") } },
                { assertDoesNotThrow { Person("Jacob O'harra") } },
                { assertDoesNotThrow { Person("Ji Lu") } }
        )
    }
}
