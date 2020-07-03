package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.order.FindOrders
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.jdbc.config.OrderJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [OrderJdbcConfig::class])
@Sql("/test-data-sales-find-orders.sql")
internal class FindOrdersTest(
        @Autowired val findOrders: FindOrders) {

    @MockBean
    private lateinit var eventPublisher: EventPublisher

    @Test
    fun order_by_id_is_found() {
        val order = findOrders!!.byId(OrderId(1))
        assertAll(
                { assertThat(order.id()).isEqualTo(OrderId(1)) },
                { assertThat(order.total()).isEqualTo(Money(1000f)) },
                { assertThat(order.items()).hasSize(2) },
                { assertThat(order.items()[0].quantity()).isEqualTo(Quantity(1)) },
                { assertThat(order.items()[1].quantity()).isEqualTo(Quantity(2)) }
        )
    }

    @Test
    fun unknown_product_found_for_an_unknown_id() {
        val order = findOrders!!.byId(OrderId(123))
        assertThat(order.id()).isEqualTo(OrderId(0))
    }
}