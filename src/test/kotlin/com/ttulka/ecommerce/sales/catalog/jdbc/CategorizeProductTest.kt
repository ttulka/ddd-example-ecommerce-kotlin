package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.FindProductsFromCategory
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.category.Uri
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
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql(statements = [
    "INSERT INTO categories VALUES ('c-1', 'test-category', 'Test');",
    "INSERT INTO products VALUES ('p-1', 'Test', 'Test', 1.00);"])
class CategorizeProductTest(
        @Autowired val fromCategory: FindProductsFromCategory,
        @Autowired val jdbcTemplate: JdbcTemplate) {

    @Test
    fun categorized_product_is_found_in_the_category() {
        val product: Product = ProductJdbc(
                ProductId("p-1"),
                Title("Test"),
                Description("Test"),
                Money(1f),
                jdbcTemplate
        )
        product.categorize(CategoryId("c-1"))

        val found = fromCategory.byUri(Uri("test-category")).toList()

        assertAll(
                { assertThat(found).hasSize(1) },
                { assertThat(found.first().id()).isEqualTo(ProductId("p-1")) }
        )
    }
}