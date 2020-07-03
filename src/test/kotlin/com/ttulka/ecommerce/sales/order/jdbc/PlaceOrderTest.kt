package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.sales.order.PlaceOrder
import com.ttulka.ecommerce.sales.order.item.OrderItem
import com.ttulka.ecommerce.sales.order.item.ProductId
import com.ttulka.ecommerce.sales.order.jdbc.config.OrderJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.offset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration

@JdbcTest
@ContextConfiguration(classes = [OrderJdbcConfig::class, PlaceOrderTest.TestConfig::class])
class PlaceOrderTest(
        @Autowired val placeOrder: PlaceOrder,
        @Autowired val eventPublisher: FakeEventPublisher) {

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun order_placed_raises_an_event() {
        placeOrder.place(OrderId("TEST123"), listOf(
                OrderItem(ProductId("test-1"), Quantity(123))),
                Money(12.34f * 123))

        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(OrderPlaced::class.java) },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).orderId).isEqualTo("TEST123") },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).items).hasSize(1) },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).items["test-1"]).isEqualTo(123) },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).total).isCloseTo(12.34f * 123, offset(0.01f)) }
        )
    }

    @Configuration
    class TestConfig {
        @Bean
        fun eventPublisher() = FakeEventPublisher()
    }

    class FakeEventPublisher : EventPublisher {

        val sent: MutableList<DomainEvent> = mutableListOf()

        override fun raise(event: DomainEvent) {
            sent.add(event)
        }
    }
}
