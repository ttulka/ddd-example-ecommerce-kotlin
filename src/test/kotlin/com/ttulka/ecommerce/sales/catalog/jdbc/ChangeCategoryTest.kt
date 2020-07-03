package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindCategories
import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.category.Title
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql(statements = ["INSERT INTO categories VALUES ('1', 'test', 'Test');"])
class ChangeCategoryTest(
        @Autowired val findCategories: FindCategories) {

    @Test
    fun product_title_is_changed() {
        val category: Category = findCategories.byId(CategoryId(1))
        category.changeTitle(Title("Updated title"))

        val productUpdated: Category = findCategories.byId(CategoryId(1))

        assertThat(productUpdated.title()).isEqualTo(Title("Updated title"))
    }
}