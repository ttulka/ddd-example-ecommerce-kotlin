package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.*
import com.ttulka.ecommerce.shipping.delivery.jdbc.config.DeliveryJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration

@JdbcTest
@ContextConfiguration(classes = [DeliveryJdbcConfig::class, PrepareDeliveryTest.TestConfig::class])
internal class PrepareDeliveryTest(
        @Autowired val findDeliveries: FindDeliveries,
        @Autowired val prepareDelivery: PrepareDelivery,
        @Autowired val eventPublisher: FakeEventPublisher) {

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun delivery_for_order_is_prepared() {
        prepareDelivery.prepare(
                OrderId("TEST123"),
                Address(Person("Test Person"), Place("Test Address 123")))
        val delivery = findDeliveries.byOrder(OrderId("TEST123"))
        assertAll(
                { assertThat(delivery.orderId()).isEqualTo(OrderId("TEST123")) },
                { assertThat(delivery.address()).isEqualTo(Address(Person("Test Person"), Place("Test Address 123"))) }
        )
    }

    @Test
    fun prepared_delivery_raises_an_event() {
        prepareDelivery.prepare(
                OrderId("TEST123"),
                Address(Person("Test Person"), Place("Test Address 123")))

        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(DeliveryPrepared::class.java) },
                { assertThat((eventPublisher.sent.first() as DeliveryPrepared).orderId).isEqualTo("TEST123") }
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
