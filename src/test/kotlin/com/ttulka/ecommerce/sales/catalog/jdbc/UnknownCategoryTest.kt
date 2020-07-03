package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.category.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class UnknownCategoryTest {

    @Test
    fun unknown_category_has_values() {
        val unknownCategory: Category = UnknownCategory()
        assertAll(
                { assertThat(unknownCategory.id()).isEqualTo(CategoryId(0)) },
                { assertThat(unknownCategory.uri()).isNotNull() },
                { assertThat(unknownCategory.title()).isNotNull() }
        )
    }

    @Test
    fun change_title_noop() {
        val unknownCategory: Category = UnknownCategory()
        val unknownTitle = unknownCategory.title()

        unknownCategory.changeTitle(Title("test"))

        assertThat(unknownCategory.title()).isEqualTo(unknownTitle)
    }
}
