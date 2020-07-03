package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Title
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Product entity.
 */
internal class ProductJdbc(
        private val id: ProductId,
        private var title: Title,
        private var description: Description,
        private var price: Money,
        private val jdbcTemplate: JdbcTemplate) : Product {

    override fun id(): ProductId = id

    override fun title(): Title = title

    override fun description(): Description = description

    override fun price(): Money = price

    override fun changeTitle(title: Title) {
        this.title = title
        jdbcTemplate.update("UPDATE products SET title = ? WHERE id = ?",
                title.value(), id.value())
    }

    override fun changeDescription(description: Description) {
        this.description = description
        jdbcTemplate.update("UPDATE products SET description = ? WHERE id = ?",
                description.value(), id.value())
    }

    override fun changePrice(price: Money) {
        this.price = price
        jdbcTemplate.update("UPDATE products SET price = ? WHERE id = ?",
                price.value(), id.value())
    }

    override fun putForSale() {
        jdbcTemplate!!.update("INSERT INTO products VALUES(?, ?, ?, ?)",
                id!!.value(), title!!.value(), description!!.value(), price!!.value())
    }

    override fun categorize(categoryId: CategoryId) {
        jdbcTemplate.update("INSERT INTO products_in_categories VALUES(?, ?)",
                id.value(), categoryId.value())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as ProductJdbc).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "ProductJdbc($id, $title, $price)"
}