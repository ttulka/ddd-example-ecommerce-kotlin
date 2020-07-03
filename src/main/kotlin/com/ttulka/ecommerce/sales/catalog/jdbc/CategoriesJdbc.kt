package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.category.*
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation of Categories collection.
 */
internal class CategoriesJdbc(
        private val query: String,
        private val queryParams: Array<Any>,
        private val jdbcTemplate: JdbcTemplate) : Categories {

    constructor(query: String, queryParam: Any, jdbcTemplate: JdbcTemplate) : this(query, arrayOf(queryParam), jdbcTemplate)
    constructor(query: String, jdbcTemplate: JdbcTemplate) : this(query, emptyArray(), jdbcTemplate)

    private fun categoriesFromDb(): Iterable<Category> =
        jdbcTemplate.queryForList("$query ORDER BY 1", *queryParams)
            .map(::toCategory)

    private fun toCategory(entry: Map<String, Any>): Category =
        CategoryJdbc(
            CategoryId(entry["id"]!!),
            Uri(entry["uri"] as String),
            Title(entry["title"] as String),
            jdbcTemplate)

    override fun iterator(): Iterator<Category> = categoriesFromDb().iterator()
}