package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.category.Title
import com.ttulka.ecommerce.sales.catalog.category.Uri
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Category entity.
 */
internal class CategoryJdbc(
        private val id: CategoryId,
        private val uri: Uri,
        private var title: Title,
        private val jdbcTemplate: JdbcTemplate) : Category {

    override fun id(): CategoryId = id

    override fun uri(): Uri = uri

    override fun title(): Title = title

    override fun changeTitle(title: Title) {
        this.title = title
        jdbcTemplate.update("UPDATE categories SET title = ? WHERE id = ?",
                title.value(), id!!.value())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as CategoryJdbc).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "CategoryJdbc($id, $uri, $title)"
}
