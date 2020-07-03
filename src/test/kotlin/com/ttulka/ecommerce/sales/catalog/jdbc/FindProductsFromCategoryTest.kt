package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindProductsFromCategory
import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql("/test-data-sales-find-products.sql")
class FindProductsFromCategoryTest(
        @Autowired val fromCategory: FindProductsFromCategory) {

    @Test
    fun products_from_a_category_by_uri_are_found() {
        val productIds = fromCategory.byUri(Uri("cat1"))
                .map(Product::id)

        assertThat(productIds).containsExactlyInAnyOrder(
                ProductId("p-1"), ProductId("p-2"))
    }
}