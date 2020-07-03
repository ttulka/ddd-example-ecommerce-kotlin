package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.*
import com.ttulka.ecommerce.shipping.delivery.Delivery.DeliveryAlreadyDispatchedException
import com.ttulka.ecommerce.shipping.delivery.Delivery.DeliveryAlreadyPreparedException
import com.ttulka.ecommerce.shipping.delivery.jdbc.config.DeliveryJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import java.util.*

@JdbcTest
@ContextConfiguration(classes = [DeliveryJdbcConfig::class, DeliveryTest.TestConfig::class])
@Sql(statements = [
    """INSERT INTO deliveries VALUES
        ('101', '1001', 'Test PersonA', 'Test Place 1'),
        ('102', '1002', 'Test PersonB', 'Test Place 2');""",
    "INSERT INTO dispatched_deliveries VALUES ('102');"])
internal class DeliveryTest(
        @Autowired val findDeliveries: FindDeliveries,
        @Autowired val eventPublisher: FakeEventPublisher) {

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun delivery_has_values() {
        val delivery = findDeliveries.byOrder(OrderId(1001))
        assertAll(
                { assertThat(delivery.id()).isEqualTo(DeliveryId(101)) },
                { assertThat(delivery.orderId()).isEqualTo(OrderId(1001)) },
                { assertThat(delivery.address()).isEqualTo(Address(Person("Test PersonA"), Place("Test Place 1"))) },
                { assertThat(delivery.isDispatched).isFalse() }
        )
    }

    @Test
    fun delivery_is_found_by_order_id() {
        val delivery = findDeliveries.byOrder(OrderId(1001))
        assertThat(delivery.id()).isEqualTo(DeliveryId(101))
    }

    @Test
    fun delivery_is_prepared(@Autowired jdbcTemplate: JdbcTemplate) {
        val randId = UUID.randomUUID().toString()
        DeliveryJdbc(DeliveryId(randId), OrderId(randId),
                Address(Person("Test Test"), Place("test")),
                false,
                jdbcTemplate, eventPublisher)
                .prepare()
        val delivery = findDeliveries.byOrder(OrderId(randId))
        assertThat(delivery.id()).isEqualTo(DeliveryId(randId))
    }

    @Test
    fun delivery_can_be_prepared_only_once() {
        val delivery = findDeliveries.byOrder(OrderId(1001))
        assertThrows<DeliveryAlreadyPreparedException> { delivery.prepare() }
    }

    @Test
    fun delivery_is_dispatched() {
        val delivery = findDeliveries.byOrder(OrderId(1001))
        delivery.dispatch()
        assertThat(delivery.isDispatched).isTrue()
    }

    @Test
    fun delivery_can_be_dispatched_only_once() {
        val delivery = findDeliveries.byOrder(OrderId(1001))
        delivery.dispatch()
        assertThrows<DeliveryAlreadyDispatchedException> { delivery.dispatch() }
    }

    @Test
    fun dispatched_delivery_can_not_be_dispatched() {
        val delivery = findDeliveries.byOrder(OrderId(1002))
        assertThrows<DeliveryAlreadyDispatchedException> { delivery.dispatch() }
    }

    @Test
    fun dispatching_a_delivery_raises_an_event() {
        val delivery = findDeliveries.byOrder(OrderId(1001))
        delivery.dispatch()

        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(DeliveryDispatched::class.java) },
                { assertThat((eventPublisher.sent.first() as DeliveryDispatched).orderId).isEqualTo("1001") }
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
