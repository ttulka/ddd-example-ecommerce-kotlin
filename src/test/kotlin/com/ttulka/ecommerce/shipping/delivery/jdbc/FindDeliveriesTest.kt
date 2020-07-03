package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.*
import com.ttulka.ecommerce.shipping.delivery.jdbc.config.DeliveryJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [DeliveryJdbcConfig::class])
@Sql(statements = ["INSERT INTO deliveries VALUES" +
        "('000101', '1001', 'Test PersonA', 'Place 1')," +
        "('000102', '1002', 'Test PersonB', 'Place 2');", "INSERT INTO dispatched_deliveries VALUES ('000102');"])
internal class FindDeliveriesTest(
        @Autowired val findDeliveries: FindDeliveries) {

    @MockBean
    private lateinit var eventPublisher: EventPublisher

    @Test
    fun all_deliveries_are_found() {
        val deliveries = findDeliveries.all().toList().sortedBy { it.id().value() }
        assertAll(
                { assertThat(deliveries).hasSizeGreaterThanOrEqualTo(2) },
                { assertThat(deliveries[0].id()).isEqualTo(DeliveryId("000101")) },
                { assertThat(deliveries[0].orderId()).isEqualTo(OrderId("1001")) },
                { assertThat(deliveries[1].id()).isEqualTo(DeliveryId("000102")) },
                { assertThat(deliveries[1].orderId()).isEqualTo(OrderId("1002")) }
        )
    }

    @Test
    fun delivery_is_found_by_order_id() {
        val delivery = findDeliveries.byOrder(OrderId("1001"))
        assertAll(
                { assertThat(delivery.id()).isEqualTo(DeliveryId("000101")) },
                { assertThat(delivery.orderId()).isEqualTo(OrderId("1001")) },
                { assertThat(delivery.address()).isEqualTo(Address(Person("Test PersonA"), Place("Place 1"))) },
                { assertThat(delivery.isDispatched).isFalse() }
        )
    }

    @Test
    fun delivery_is_not_found_by_order_id() {
        val delivery = findDeliveries.byOrder(OrderId("does not exist"))
        assertThat(delivery.id()).isEqualTo(DeliveryId(0))
    }

    @Test
    fun status_is_merged_with_events_ledger() {
        val delivery = findDeliveries.byOrder(OrderId("1002"))
        assertThat(delivery.isDispatched).isTrue()
    }

    @Test
    fun delivery_for_an_order_is_prepared() {
        val isPrepared = findDeliveries.isPrepared(OrderId("1001"))
        assertThat(isPrepared).isTrue()
    }

    @Test
    fun delivery_for_an_order_is_not_prepared() {
        val isPrepared = findDeliveries.isPrepared(OrderId("WRONG"))
        assertThat(isPrepared).isFalse()
    }
}
