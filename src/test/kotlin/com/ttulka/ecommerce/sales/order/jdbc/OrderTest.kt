package com.ttulka.ecommerce.sales.order.jdbc

import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.order.Order
import com.ttulka.ecommerce.sales.order.Order.OrderHasNoItemsException
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.OrderPlaced
import com.ttulka.ecommerce.sales.order.PlaceableOrder
import com.ttulka.ecommerce.sales.order.item.OrderItem
import com.ttulka.ecommerce.sales.order.item.ProductId
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.offset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate

@JdbcTest
class OrderTest(
        @Autowired val jdbcTemplate: JdbcTemplate) {

    private val eventPublisher = object : EventPublisher {
        val sent: MutableList<DomainEvent> = mutableListOf()

        override fun raise(event: DomainEvent) {
            sent.add(event)
        }
    }

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun items_are_returned() {
        val order: Order = OrderJdbc(OrderId("TEST123"), Money(3f), listOf(
                OrderItem(ProductId("test-1"), Quantity(1)),
                OrderItem(ProductId("test-2"), Quantity(2))),
                jdbcTemplate, eventPublisher)
        assertAll(
                { assertThat(order.items()).hasSize(2) },
                { assertThat(order.items()[0].quantity()).isEqualTo(Quantity(1)) },
                { assertThat(order.items()[1].quantity()).isEqualTo(Quantity(2)) }
        )
    }

    @Test
    fun order_contains_at_least_one_item() {
        assertThrows<OrderHasNoItemsException> {
            OrderJdbc(OrderId("TEST123"), Money.ZERO, emptyList(),
                    jdbcTemplate, eventPublisher)
        }
    }

    @Test
    fun placed_order_raises_an_event() {
        val order: PlaceableOrder = OrderJdbc(OrderId("TEST123"), Money(12.34f * 123), listOf(
                OrderItem(ProductId("test-1"), Quantity(123))),
                jdbcTemplate, eventPublisher)
        order.place()

        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(OrderPlaced::class.java) },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).orderId).isEqualTo("TEST123") },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).items).hasSize(1) },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).items["test-1"]).isEqualTo(123) },
                { assertThat((eventPublisher.sent.first() as OrderPlaced).total).isCloseTo(12.34f * 123, offset(0.01f)) }
        )
    }

    @Test
    fun order_can_be_placed_only_once() {
        val order: PlaceableOrder = OrderJdbc(
                OrderId("TEST123"), Money(12.34f),
                listOf(OrderItem(ProductId("test-1"), Quantity(123))),
                jdbcTemplate, eventPublisher)
        order.place()

        assertAll(
                { assertThrows<PlaceableOrder.OrderAlreadyPlacedException> { order.place() } },
                { assertThat(eventPublisher.sent).hasSize(1) }, // not two events
                { assertThat(eventPublisher.sent.first()).isInstanceOf(OrderPlaced::class.java) }
        )
    }
}
