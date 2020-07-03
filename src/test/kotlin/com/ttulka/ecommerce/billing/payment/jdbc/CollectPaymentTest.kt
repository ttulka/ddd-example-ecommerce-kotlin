package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.CollectPayment
import com.ttulka.ecommerce.billing.payment.PaymentCollected
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.billing.payment.jdbc.config.PaymentJdbcConfig
import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
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
@ContextConfiguration(classes = [PaymentJdbcConfig::class, CollectPaymentTest.TestConfig::class])
internal class CollectPaymentTest(
        @Autowired val collectPayment: CollectPayment,
        @Autowired val eventPublisher: FakeEventPublisher) {

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun payment_confirmation_raises_an_event() {
        collectPayment.collect(ReferenceId("TEST123"), Money(123.5f))
        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(PaymentCollected::class.java) },
                { assertThat((eventPublisher.sent.first() as PaymentCollected).referenceId).isEqualTo("TEST123") }
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