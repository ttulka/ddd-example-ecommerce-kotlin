package com.ttulka.ecommerce.warehouse.jdbc

import com.ttulka.ecommerce.warehouse.Amount
import com.ttulka.ecommerce.warehouse.InStock
import com.ttulka.ecommerce.warehouse.ProductId
import com.ttulka.ecommerce.warehouse.Warehouse
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Warehouse use-cases.
 */
internal class WarehouseJdbc(
        private val jdbcTemplate: JdbcTemplate) : Warehouse {

    override fun leftInStock(productId: ProductId): InStock =
        jdbcTemplate.queryForList(
            "SELECT amount FROM products_in_stock WHERE product_id = ?",
            Int::class.java, productId.value()).firstOrNull()
            ?.let { InStock(Amount(it)) }
            ?: InStock(Amount.ZERO)

    override fun putIntoStock(productId: ProductId, amount: Amount) {
        jdbcTemplate.update(
            "INSERT INTO products_in_stock VALUES (?, ?)",
            productId.value(), amount.value())
    }
}