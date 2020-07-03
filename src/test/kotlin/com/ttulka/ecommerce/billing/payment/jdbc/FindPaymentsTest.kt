package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.FindPayments
import com.ttulka.ecommerce.billing.payment.Payment
import com.ttulka.ecommerce.billing.payment.PaymentId
import com.ttulka.ecommerce.billing.payment.ReferenceId
import com.ttulka.ecommerce.billing.payment.jdbc.config.PaymentJdbcConfig
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [PaymentJdbcConfig::class])
@Sql("/test-data-billing-find-payments.sql")
internal class FindPaymentsTest(
        @Autowired val findPayments: FindPayments) {

    @MockBean
    private lateinit var eventPublisher: EventPublisher

    @Test
    fun all_payments_are_found() {
        val payments: List<Payment> = findPayments.all().toList()
        assertAll(
                { assertThat(payments).hasSize(2) },
                { assertThat(payments[0].id()).isEqualTo(PaymentId(101)) },
                { assertThat(payments[0].referenceId()).isEqualTo(ReferenceId(1001)) },
                { assertThat(payments[0].total()).isEqualTo(Money(100.5f)) },
                { assertThat(payments[0].isRequested).isTrue() },
                { assertThat(payments[0].isCollected).isFalse() },
                { assertThat(payments[1].id()).isEqualTo(PaymentId(102)) },
                { assertThat(payments[1].referenceId()).isEqualTo(ReferenceId(1002)) },
                { assertThat(payments[1].total()).isEqualTo(Money(200.5f)) },
                { assertThat(payments[1].isRequested).isTrue() },
                { assertThat(payments[1].isCollected).isTrue() }
        )
    }
}