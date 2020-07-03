package com.ttulka.ecommerce.billing.payment.jdbc

import com.ttulka.ecommerce.billing.payment.FindPayments
import com.ttulka.ecommerce.billing.payment.Payment
import com.ttulka.ecommerce.billing.payment.PaymentId
import com.ttulka.ecommerce.billing.payment.jdbc.config.PaymentJdbcConfig
import com.ttulka.ecommerce.common.events.EventPublisher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [PaymentJdbcConfig::class])
@Sql(statements = ["INSERT INTO payments VALUES " +
        "('000', 'REF01', 1.0, 'NEW'), " +
        "('001', 'REF02', 2.0, 'NEW'), " +
        "('002', 'REF03', 3.0, 'NEW')"])
internal class PaymentsTest(
        @Autowired val findPayments: FindPayments) {

    @MockBean
    lateinit var eventPublisher: EventPublisher

    @Test
    fun payments_are_streamed() {
        val payments = findPayments.all()
        val list: List<Payment> = payments.toList()
        assertAll(
                { assertThat(list.size).isEqualTo(3) },
                { assertThat(list[0].id()).isEqualTo(PaymentId("000")) },
                { assertThat(list[1].id()).isEqualTo(PaymentId("001")) },
                { assertThat(list[2].id()).isEqualTo(PaymentId("002")) }
        )
    }

    @Test
    fun payments_are_limited() {
        val payments = findPayments.all()
                .range(2, 1)
        val list: List<Payment> = payments.toList()
        assertAll(
                { assertThat(list.size).isEqualTo(1) },
                { assertThat(list[0].id()).isEqualTo(PaymentId("002")) }
        )
    }

    @Test
    fun limited_start_is_greater_than_zero() {
        val payments = findPayments.all()
        assertAll({
            assertThrows<IllegalArgumentException> { payments.range(-1, 1) }
        }, {
            assertThrows<IllegalArgumentException> { payments.range(1).range(-1, 1) }
        })
    }

    @Test
    fun limited_limit_is_greater_than_zero() {
        val payments = findPayments.all()
        assertAll({
            assertThrows<IllegalArgumentException> { payments.range(0) }
        }, {
            assertThrows<IllegalArgumentException> { payments.range(-1) }
        }, {
            assertThrows<IllegalArgumentException> { payments.range(1).range(0) }
        }, {
            assertThrows<IllegalArgumentException> { payments.range(1).range(-1) }
        }
        )
    }
}
