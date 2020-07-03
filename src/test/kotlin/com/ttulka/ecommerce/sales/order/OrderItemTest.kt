package com.ttulka.ecommerce.sales.order

import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.order.item.OrderItem
import com.ttulka.ecommerce.sales.order.item.ProductId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderItemTest {

    @Test
    fun order_item_is_created() {
        val orderItem = OrderItem(ProductId("test-1"), Quantity(123))
        assertThat(orderItem.quantity()).isEqualTo(Quantity(123))
    }
}