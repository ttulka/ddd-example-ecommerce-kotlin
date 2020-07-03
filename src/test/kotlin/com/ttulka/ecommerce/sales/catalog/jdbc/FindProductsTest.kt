package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql("/test-data-sales-find-products.sql")
class FindProductsTest(
        @Autowired val findProducts: FindProducts) {

    @Test
    fun all_products_are_found() {
        val productIds = findProducts.all()
                .map(Product::id)

        assertThat(productIds).containsExactlyInAnyOrder(
                ProductId("p-1"), ProductId("p-2"), ProductId("p-3"))
    }

    @Test
    fun product_by_id_is_found() {
        val product = findProducts.byId(ProductId("p-1"))
        assertAll(
                { assertThat(product.id()).isEqualTo(ProductId("p-1")) },
                { assertThat(product.title()).isEqualTo(Title("Prod 1")) },
                { assertThat(product.description()).isEqualTo(Description("Prod 1 Desc")) },
                { assertThat(product.price()).isEqualTo(Money(1f)) }
        )
    }

    @Test
    fun unknown_product_found_for_an_unknown_id() {
        val product = findProducts.byId(ProductId("there's no such an ID"))

        assertThat(product.id()).isEqualTo(ProductId(0))
    }
}
