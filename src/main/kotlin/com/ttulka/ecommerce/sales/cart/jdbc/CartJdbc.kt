package com.ttulka.ecommerce.sales.cart.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.CartId
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId
import com.ttulka.ecommerce.sales.cart.item.Title
import org.springframework.jdbc.core.JdbcTemplate
import java.math.BigDecimal

internal class CartJdbc(
        private val id: CartId,
        private val jdbcTemplate: JdbcTemplate) : Cart {

    private val items: List<CartItem> by lazy { itemsFromDb() }

    override fun id(): CartId = id

    override fun items(): List<CartItem> = items

    private fun itemsFromDb(): List<CartItem> =
        jdbcTemplate.queryForList(
            """SELECT product_id, title, price, quantity FROM cart_items
                WHERE cart_id = ?""", id.value())
            .map(::toCartItem)

    private fun toCartItem(entry: Map<String, Any>): CartItem =
        CartItem(
            ProductId(entry["product_id"]!!),
            Title(entry["title"] as String),
            Money((entry["price"] as BigDecimal).toFloat()),
            Quantity(entry["quantity"] as Int))

    override fun hasItems(): Boolean =
        jdbcTemplate.queryForObject(
            "SELECT COUNT(cart_id) FROM cart_items WHERE cart_id = ?",
            Int::class.java, id.value()) > 0

    override fun add(toAdd: CartItem) {
        if (hasItem(toAdd)) {
            jdbcTemplate.update(
                """UPDATE cart_items SET quantity = quantity + ?
                        WHERE cart_id = ? AND product_id = ? AND title = ? AND price = ?""",
                toAdd.quantity().value(), id.value(),
                toAdd.productId().value(), toAdd.title().value(), toAdd.unitPrice().value())
        } else {
            jdbcTemplate.update(
                "INSERT INTO cart_items VALUES (?, ?, ?, ?, ?)",
                toAdd.productId().value(), toAdd.title().value(), toAdd.unitPrice().value(),
                toAdd.quantity().value(), id.value())
        }
    }

    private fun hasItem(item: CartItem): Boolean =
        jdbcTemplate.queryForObject(
            """SELECT COUNT(product_id) FROM cart_items 
                WHERE product_id = ? AND title = ? AND price = ?""", Int::class.java,
            item.productId().value(), item.title().value(), item.unitPrice().value()) > 0

    override fun remove(productId: ProductId) {
        jdbcTemplate.update(
            "DELETE FROM cart_items WHERE product_id = ? AND cart_id = ?",
            productId.value(), id.value())
    }

    override fun empty() {
        jdbcTemplate.update("DELETE FROM cart_items WHERE cart_id = ?", id.value())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as CartJdbc).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "CartJdbc($id)"
}
