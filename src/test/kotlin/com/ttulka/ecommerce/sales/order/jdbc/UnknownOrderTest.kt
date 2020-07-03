package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.sales.order.Order
import com.ttulka.ecommerce.sales.order.OrderId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class UnknownOrderTest {

    @Test
    fun unknown_product_has_values() {
        val unknownOrder: Order = UnknownOrder()
        assertAll(
                { assertThat(unknownOrder.id()).isEqualTo(OrderId(0)) },
                { assertThat(unknownOrder.items()).hasSize(0) }
        )
    }
}