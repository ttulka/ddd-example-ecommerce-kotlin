package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindProductsFromCategory
import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.product.Products
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Find Products from Category use-cases.
 */
internal class FindProductsFromCategoryJdbc(
        private val jdbcTemplate: JdbcTemplate) : FindProductsFromCategory {

    override fun byUri(categoryUri: Uri): Products {
        return ProductsJdbc("""SELECT p.id, p.title, p.description, p.price FROM products AS p 
                                JOIN products_in_categories AS pc ON pc.product_id = p.id 
                                JOIN categories AS c ON c.id = pc.category_id 
                                WHERE c.uri = ?""",
                categoryUri.value(), jdbcTemplate)
    }
}
