package com.ttulka.ecommerce.sales.cart.jdbc

import com.ttulka.ecommerce.sales.cart.CartId
import com.ttulka.ecommerce.sales.cart.RetrieveCart
import com.ttulka.ecommerce.sales.cart.jdbc.config.CartJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration

@JdbcTest
@ContextConfiguration(classes = [CartJdbcConfig::class])
internal class RetrieveCartTest(
        @Autowired val retrieveCart: RetrieveCart) {

    @Test
    fun cart_is_retrieved() {
        val cart = retrieveCart.byId(CartId("TEST"))
        assertThat(cart.id()).isEqualTo(CartId("TEST"))
    }
}