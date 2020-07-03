package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Products
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Find Products use-cases.
 */
internal class FindProductsJdbc(
        private val jdbcTemplate: JdbcTemplate) : FindProducts {

    override fun all(): Products {
        return ProductsJdbc("SELECT id, title, description, price FROM products",
                jdbcTemplate)
    }

    override fun byId(id: ProductId): Product {
        return ProductsJdbc("SELECT id, title, description, price FROM products WHERE id = ?",
                id.value(), jdbcTemplate).firstOrNull()
                ?: UnknownProduct()
    }
}