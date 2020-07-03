package com.ttulka.ecommerce.shipping.delivery

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class AddressTest {

    @Test
    fun address_values() {
        val person = Person("Test Person")
        val place = Place("Test Address 123")
        val address = Address(person, place)
        assertAll(
                { assertThat(address.person()).isEqualTo(person) },
                { assertThat(address.place()).isEqualTo(place) }
        )
    }
}