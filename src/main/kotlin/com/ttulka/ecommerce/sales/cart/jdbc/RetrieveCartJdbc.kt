package com.ttulka.ecommerce.sales.cart.jdbc

import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.CartId
import com.ttulka.ecommerce.sales.cart.RetrieveCart
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation for Retrieve Cart use-cases.
 */
internal class RetrieveCartJdbc(
        private val jdbcTemplate: JdbcTemplate) : RetrieveCart {

    override fun byId(cartId: CartId): Cart = CartJdbc(cartId, jdbcTemplate)
}
