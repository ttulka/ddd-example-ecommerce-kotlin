package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql(statements = ["INSERT INTO products VALUES " +
        "('000', 'A', 'desc0', 1.0), " +
        "('001', 'B', 'desc1', 2.0), " +
        "('002', 'C', 'desc2', 3.0)"])
class ProductsTest(
        @Autowired val findProducts: FindProducts) {

    @Test
    fun products_are_streamed() {
        val products = findProducts.all().toList()
        assertAll(
                { assertThat(products.size).isEqualTo(3) },
                { assertThat(products[0].id()).isEqualTo(ProductId("000")) },
                { assertThat(products[1].id()).isEqualTo(ProductId("001")) },
                { assertThat(products[2].id()).isEqualTo(ProductId("002")) }
        )
    }

    @Test
    fun products_are_limited() {
        val products = findProducts.all()
                .range(2, 1).toList()
        assertAll(
                { assertThat(products.size).isEqualTo(1) },
                { assertThat(products[0].id()).isEqualTo(ProductId("002")) }
        )
    }

    @Test
    fun limited_start_is_greater_than_zero() {
        val products = findProducts.all()
        assertAll({
            assertThrows<IllegalArgumentException> { products.range(-1, 1) }
        }, {
            assertThrows<IllegalArgumentException> { products.range(1).range(-1, 1) }
        })
    }

    @Test
    fun limited_limit_is_greater_than_zero() {
        val products = findProducts.all()
        assertAll({
            assertThrows<IllegalArgumentException> { products.range(0) }
        }, {
            assertThrows<IllegalArgumentException> { products.range(-1) }
        }, {
            assertThrows<IllegalArgumentException> { products.range(1).range(0) }
        }, {
            assertThrows<IllegalArgumentException> { products.range(1).range(-1) }
        })
    }
}