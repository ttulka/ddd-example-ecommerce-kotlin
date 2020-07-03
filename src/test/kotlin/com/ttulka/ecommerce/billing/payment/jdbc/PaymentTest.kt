package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.Payment
import com.ttulka.ecommerce.billing.payment.Payment.*
import com.ttulka.ecommerce.billing.payment.PaymentCollected
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
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

@JdbcTest
@ContextConfiguration(classes = [PaymentTest.TestConfig::class])
internal class PaymentTest(
        @Autowired val jdbcTemplate: JdbcTemplate,
        @Autowired val eventPublisher: FakeEventPublisher) {

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun payment_values() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)

        assertAll(
                { assertThat(payment.id()).isNotNull() },
                { assertThat(payment.referenceId()).isEqualTo(ReferenceId(123)) },
                { assertThat(payment.total()).isEqualTo(Money(123.5f)) }
        )
    }

    @Test
    fun payment_is_requested() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)
        payment.request()

        assertAll(
                { assertThat(payment.isRequested).isTrue() },
                { assertThat(payment.isCollected).isFalse() }
        )
    }

    @Test
    fun payment_is_collected() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)
        payment.request()
        payment.collect()

        assertAll(
                { assertThat(payment.isRequested).isTrue() },
                { assertThat(payment.isCollected).isTrue() }
        )
    }

    @Test
    fun collected_payment_raises_an_event() {
        val payment: Payment = PaymentJdbc(
                ReferenceId("TEST123"), Money(123.5f), jdbcTemplate, eventPublisher)
        payment.request()
        payment.collect()

        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(PaymentCollected::class.java) },
                { assertThat((eventPublisher.sent.first() as PaymentCollected).referenceId).isEqualTo("TEST123") }
        )
    }

    @Test
    fun cannot_request_already_requested_payment() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)
        payment.request()

        assertThrows<PaymentAlreadyRequestedException> { payment.request() }
    }

    @Test
    fun cannot_request_already_collected_payment() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)
        payment.request()
        payment.collect()

        assertThrows<PaymentAlreadyRequestedException> { payment.request() }
    }

    @Test
    fun cannot_collect_unrequested_payment() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)

        assertThrows<PaymentNotRequestedYetException> { payment.collect() }
    }

    @Test
    fun cannot_collect_already_collected_payment() {
        val payment: Payment = PaymentJdbc(
                ReferenceId(123), Money(123.5f), jdbcTemplate, eventPublisher)
        payment.request()
        payment.collect()

        assertThrows<PaymentAlreadyCollectedException> { payment.collect() }
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
