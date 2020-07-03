package com.ttulka.ecommerce.warehouse.jdbc

import com.ttulka.ecommerce.common.events.DomainEvent
import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.warehouse.*
import com.ttulka.ecommerce.warehouse.jdbc.config.WarehouseJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@JdbcTest
@ContextConfiguration(classes = [WarehouseJdbcConfig::class, FetchGoodsTest.TestConfig::class])
@Transactional(propagation = Propagation.NOT_SUPPORTED)
internal class FetchGoodsTest(
        @Autowired val fetchGoods: FetchGoods,
        @Autowired val warehouse: Warehouse,
        @Autowired val eventPublisher: FakeEventPublisher) {

    @BeforeEach
    fun clearEventPublisher() {
        eventPublisher.sent.clear()
    }

    @Test
    fun fetched_goods_raises_an_event() {
        val productCode = productCodeInStock(1)
        fetchGoods.fetchFromOrder(OrderId("TEST123"), listOf(
                ToFetch(ProductId(productCode), Amount(1))))

        assertAll(
                { assertThat(eventPublisher.sent).hasSize(1) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(GoodsFetched::class.java) },
                { assertThat((eventPublisher.sent.first() as GoodsFetched).orderId).isEqualTo("TEST123") }
        )
    }

    @Test
    fun fetching_decrease_amount_in_the_stock() {
        val productCode = productCodeInStock(2)
        fetchGoods.fetchFromOrder(OrderId(123), listOf(
                ToFetch(ProductId(productCode), Amount(1))))

        assertThat(warehouse.leftInStock(ProductId(productCode))).isEqualTo(InStock(Amount(1)))
    }

    @Test
    fun cannot_decrease_amount_under_zero() {
        val productCode = productCodeInStock(1)
        fetchGoods.fetchFromOrder(OrderId(123), listOf(
                ToFetch(ProductId(productCode), Amount(2))))

        assertThat(warehouse.leftInStock(ProductId(productCode))).isEqualTo(InStock(Amount.ZERO))
    }

    @Test
    fun missed_goods_raises_an_event() {
        val productCode = productCodeInStock(1)
        fetchGoods.fetchFromOrder(OrderId(123), listOf(
                ToFetch(ProductId(productCode), Amount(99))))
        assertAll(
                { assertThat(eventPublisher.sent).hasSizeBetween(1, 2) },
                { assertThat(eventPublisher.sent.first()).isInstanceOf(GoodsMissed::class.java) },
                { assertThat((eventPublisher.sent.first() as GoodsMissed).productCode).isEqualTo(productCode) },
                { assertThat((eventPublisher.sent.first() as GoodsMissed).amount).isEqualTo(98) }
        )
    }

    fun productCodeInStock(amount: Int): String {
        val productCode = UUID.randomUUID().toString()
        warehouse.putIntoStock(ProductId(productCode), Amount(amount))
        return productCode
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
