package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.*
import com.ttulka.ecommerce.shipping.delivery.jdbc.config.DeliveryJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@JdbcTest
@ContextConfiguration(classes = [DeliveryJdbcConfig::class])
@Transactional(propagation = Propagation.NOT_SUPPORTED)
internal class DispatchDeliveryTest(
        @Autowired val findDeliveries: FindDeliveries,
        @Autowired val prepareDelivery: PrepareDelivery,
        @Autowired val dispatchDelivery: DispatchDelivery) {

    @MockBean
    private lateinit var eventPublisher: EventPublisher

    @Test
    fun delivery_is_dispatched() {
        val orderId = prepareOrder()
        dispatchDelivery.byOrder(orderId)

        val delivery = findDeliveries.byOrder(orderId)

        assertThat(delivery.isDispatched).isTrue()
    }

    private fun prepareOrder(): OrderId {
        val orderId = OrderId(UUID.randomUUID())
        prepareDelivery.prepare(orderId, Address(Person("Test Test"), Place("Test Test 123")))
        return orderId
    }
}