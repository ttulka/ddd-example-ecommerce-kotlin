package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindCategories
import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.category.Title
import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
@Sql(statements = ["INSERT INTO categories VALUES ('1', 'cat1', 'Cat 1'), (2, 'cat2', 'Cat 2');"])
internal class FindCategoriesTest(
        @Autowired val findCategories: FindCategories) {

    @Test
    fun all_categories_are_found() {
        val categoryIds = findCategories.all()
                .map(Category::id)

        assertThat(categoryIds).containsExactlyInAnyOrder(
                CategoryId(1), CategoryId(2))
    }

    @Test
    fun category_by_id_is_found() {
        val category: Category = findCategories.byId(CategoryId(1))
        assertAll(
                { assertThat(category.id()).isEqualTo(CategoryId(1)) },
                { assertThat(category.uri()).isEqualTo(Uri("cat1")) },
                { assertThat(category.title()).isEqualTo(Title("Cat 1")) }
        )
    }

    @Test
    fun unknown_category_found_for_unknown_id() {
        val category: Category = findCategories.byId(CategoryId("does not exist"))

        assertThat(category.id()).isEqualTo(CategoryId(0))
    }
}