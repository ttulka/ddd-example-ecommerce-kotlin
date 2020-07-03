package com.ttulka.ecommerce.warehouse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class ToFetchTest {

    @Test
    fun to_fetch_values() {
        val toFetch = ToFetch(ProductId("test"), Amount(123))
        assertAll(
                { assertThat(toFetch.productId()).isEqualTo(ProductId("test")) },
                { assertThat(toFetch.amount()).isEqualTo(Amount(123)) }
        )
    }
}