package com.ttulka.ecommerce.shipping.dispatching.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.DispatchDelivery
import com.ttulka.ecommerce.shipping.dispatching.DispatchingSaga
import com.ttulka.ecommerce.shipping.dispatching.OrderId
import com.ttulka.ecommerce.shipping.dispatching.jdbc.config.DispatchingSagaJdbcConfig
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration

@JdbcTest
@ContextConfiguration(classes = [DispatchingSagaJdbcConfig::class])
internal class DispatchingSagaTest(
        @Autowired val saga: DispatchingSaga) {

    @MockBean
    private lateinit var dispatchDelivery: DispatchDelivery

    @MockBean
    private lateinit var eventPublisher: EventPublisher

    @Test
    fun delivery_is_dispatched() {
        saga.prepared(OrderId("TEST"))
        saga.accepted(OrderId("TEST"))
        saga.fetched(OrderId("TEST"))
        saga.paid(OrderId("TEST"))

        verify(dispatchDelivery).byOrder(com.ttulka.ecommerce.shipping.delivery.OrderId("TEST"))
    }

    @Test
    fun not_paid_delivery_is_not_dispatched() {
        saga.prepared(OrderId("TEST"))
        saga.accepted(OrderId("TEST"))
        saga.fetched(OrderId("TEST"))
        //saga.paid(new SagaId("TEST"));

        verifyNoInteractions(dispatchDelivery)
    }

    @Test
    fun not_fetched_delivery_is_not_dispatched() {
        saga.prepared(OrderId("TEST"))
        saga.accepted(OrderId("TEST"))
        //saga.fetched(new SagaId("TEST"));
        saga.paid(OrderId("TEST"))

        verifyNoInteractions(dispatchDelivery)
    }

    @Test
    fun not_accepted_delivery_is_not_dispatched() {
        saga.prepared(OrderId("TEST"))
        //saga.accepted(new SagaId("TEST"));
        saga.fetched(OrderId("TEST"))
        saga.paid(OrderId("TEST"))

        verifyNoInteractions(dispatchDelivery)
    }

    @Test
    fun not_prepared_delivery_is_not_dispatched() {
        //saga.prepared(new SagaId("TEST"));
        saga.accepted(OrderId("TEST"))
        saga.fetched(OrderId("TEST"))
        saga.paid(OrderId("TEST"))

        verifyNoInteractions(dispatchDelivery)
    }
}
