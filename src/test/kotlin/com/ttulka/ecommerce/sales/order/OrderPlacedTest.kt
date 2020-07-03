package com.ttulka.ecommerce.sales.order

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.Instant

class OrderPlacedTest {

    @Test
    fun order_id_is_set() {
        val (_, orderId) = fakeOrderPlaced()
        Assertions.assertThat(orderId).isEqualTo("TEST123")
    }

    @Test
    fun order_items_are_set() {
        val (_, _, items) = fakeOrderPlaced()
        assertAll(
                { assertThat(items).hasSize(2) },
                { assertThat(items["test-1"]).isEqualTo(1) },
                { assertThat(items["test-2"]).isEqualTo(2) }
        )
    }

    private fun fakeOrderPlaced(): OrderPlaced {
        return OrderPlaced(
                Instant.now(),
                "TEST123",
                mapOf("test-1" to 1, "test-2" to 2), 5f)
    }
}
