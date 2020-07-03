package com.ttulka.ecommerce.warehouse.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.warehouse.Amount
import com.ttulka.ecommerce.warehouse.InStock
import com.ttulka.ecommerce.warehouse.ProductId
import com.ttulka.ecommerce.warehouse.Warehouse
import com.ttulka.ecommerce.warehouse.jdbc.config.WarehouseJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [WarehouseJdbcConfig::class])
@Sql(statements = ["INSERT INTO products_in_stock VALUES ('test-1', 123), ('test-2', 321);"])
internal class WarehouseTest(
        @Autowired val warehouse: Warehouse) {
    
    @MockBean
    private lateinit var eventPublisher: EventPublisher

    @Test
    fun left_in_stock_returned() {
        val inStock = warehouse.leftInStock(ProductId("test-1"))
        assertThat(inStock).isEqualTo(InStock(Amount(123)))
    }

    @Test
    fun zero_left_in_stock_returned_for_an_unknown_product() {
        val inStock = warehouse.leftInStock(ProductId("XXX"))
        assertThat(inStock).isEqualTo(InStock(Amount.ZERO))
    }

    @Test
    fun product_is_put_into_stock() {
        warehouse.putIntoStock(ProductId("test-xxx"), Amount(123))
        assertThat(warehouse.leftInStock(ProductId("test-xxx"))).isEqualTo(InStock(Amount(123)))
    }
}