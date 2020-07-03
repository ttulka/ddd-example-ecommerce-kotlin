package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindCategories
import com.ttulka.ecommerce.sales.catalog.category.Categories
import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Find Categories use-cases.
 */
internal class FindCategoriesJdbc(
        private val jdbcTemplate: JdbcTemplate) : FindCategories {

    override fun all(): Categories =
            CategoriesJdbc("SELECT id, uri, title FROM categories", jdbcTemplate)

    override fun byId(id: CategoryId): Category =
            CategoriesJdbc("SELECT id, uri, title FROM categories WHERE id = ?",
                    id.value(), jdbcTemplate).firstOrNull()
                    ?: UnknownCategory()
}
