package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql(statements = ["INSERT INTO products VALUES ('TEST1', 'Test', 'Test', 1.00);"])
class ChangeProductTest(
        @Autowired val findProducts: FindProducts) {

    @Test
    fun product_title_is_changed() {
        val product = findProducts.byId(ProductId("TEST1"))
        product.changeTitle(Title("Updated title"))

        val updated = findProducts.byId(ProductId("TEST1"))
        assertThat(updated.title()).isEqualTo(Title("Updated title"))
    }

    @Test
    fun product_description_is_changed() {
        val product = findProducts.byId(ProductId("TEST1"))
        product.changeDescription(Description("Updated description"))

        val updated = findProducts.byId(ProductId("TEST1"))
        assertThat(updated.description()).isEqualTo(Description("Updated description"))
    }

    @Test
    fun product_price_is_changed() {
        val product = findProducts.byId(ProductId("TEST1"))
        product.changePrice(Money(100.5f))

        val updated = findProducts.byId(ProductId("TEST1"))
        assertThat(updated.price()).isEqualTo(Money(100.5f))
    }
}