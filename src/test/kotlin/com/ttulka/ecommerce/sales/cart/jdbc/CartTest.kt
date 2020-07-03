package com.ttulka.ecommerce.sales.cart.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.CartId
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId
import com.ttulka.ecommerce.sales.cart.item.Title
import com.ttulka.ecommerce.sales.cart.jdbc.config.CartJdbcConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql

@JdbcTest
@ContextConfiguration(classes = [CartJdbcConfig::class])
@Sql(statements = [
    "INSERT INTO cart_items VALUES ('P001', 'Test 1', 12.3, 1, 'C001');",
    "INSERT INTO cart_items VALUES ('P002', 'Test 2', 10.5, 2, 'C001');"])
class CartTest(
        @Autowired val jdbcTemplate: JdbcTemplate) {

    @Test
    fun cart_is_empty() {
        val cart: Cart = CartJdbc(CartId("WRONG"), jdbcTemplate)
        assertAll(
                { assertThat(cart.hasItems()).isFalse() },
                { assertThat(cart.items()).isEmpty() }
        )
    }

    @Test
    fun cart_has_items() {
        val cart: Cart = CartJdbc(CartId("C001"), jdbcTemplate)
        assertAll(
                { assertThat(cart.hasItems()).isTrue() },
                { assertThat(cart.items()).hasSize(2) },
                { assertThat(cart.items()[0].productId()).isEqualTo(ProductId("P001")) },
                { assertThat(cart.items()[0].title()).isEqualTo(Title("Test 1")) },
                { assertThat(cart.items()[0].unitPrice()).isEqualTo(Money(12.3f)) },
                { assertThat(cart.items()[0].quantity()).isEqualTo(Quantity(1)) },
                { assertThat(cart.items()[1].productId()).isEqualTo(ProductId("P002")) },
                { assertThat(cart.items()[1].title()).isEqualTo(Title("Test 2")) },
                { assertThat(cart.items()[1].unitPrice()).isEqualTo(Money(10.5f)) },
                { assertThat(cart.items()[1].quantity()).isEqualTo(Quantity(2)) }
        )
    }

    @Test
    fun item_is_added() {
        val cart: Cart = CartJdbc(CartId("CTEST"), jdbcTemplate)
        cart.add(CartItem(ProductId("PTEST"), Title("Test"), Money(1.5f), Quantity(123)))
        assertAll(
                { assertThat(cart.hasItems()).isTrue() },
                { assertThat(cart.items()).hasSize(1) },
                { assertThat(cart.items()[0].productId()).isEqualTo(ProductId("PTEST")) },
                { assertThat(cart.items()[0].title()).isEqualTo(Title("Test")) },
                { assertThat(cart.items()[0].unitPrice()).isEqualTo(Money(1.5f)) },
                { assertThat(cart.items()[0].quantity()).isEqualTo(Quantity(123)) }
        )
    }

    @Test
    fun items_are_added() {
        val cart: Cart = CartJdbc(CartId("C001"), jdbcTemplate)
        cart.add(CartItem(ProductId("PTEST"), Title("Test"), Money(1.5f), Quantity(123)))
        assertAll(
                { assertThat(cart.hasItems()).isTrue() },
                { assertThat(cart.items()).hasSize(3) },
                { assertThat(cart.items()[0].productId()).isEqualTo(ProductId("P001")) },
                { assertThat(cart.items()[0].title()).isEqualTo(Title("Test 1")) },
                { assertThat(cart.items()[0].unitPrice()).isEqualTo(Money(12.3f)) },
                { assertThat(cart.items()[0].quantity()).isEqualTo(Quantity(1)) },
                { assertThat(cart.items()[1].productId()).isEqualTo(ProductId("P002")) },
                { assertThat(cart.items()[1].title()).isEqualTo(Title("Test 2")) },
                { assertThat(cart.items()[1].unitPrice()).isEqualTo(Money(10.5f)) },
                { assertThat(cart.items()[1].quantity()).isEqualTo(Quantity(2)) },
                { assertThat(cart.items()[2].productId()).isEqualTo(ProductId("PTEST")) },
                { assertThat(cart.items()[2].title()).isEqualTo(Title("Test")) },
                { assertThat(cart.items()[2].unitPrice()).isEqualTo(Money(1.5f)) },
                { assertThat(cart.items()[2].quantity()).isEqualTo(Quantity(123)) }
        )
    }

    @Test
    fun quantity_is_increased() {
        val cart: Cart = CartJdbc(CartId("CTEST"), jdbcTemplate)
        cart.add(CartItem(ProductId("PTEST"), Title("Test"), Money(1.5f), Quantity(123)))
        cart.add(CartItem(ProductId("PTEST"), Title("Test"), Money(1.5f), Quantity(321)))
        assertAll(
                { assertThat(cart.items()).hasSize(1) },
                { assertThat(cart.items()[0].productId()).isEqualTo(ProductId("PTEST")) },
                { assertThat(cart.items()[0].title()).isEqualTo(Title("Test")) },
                { assertThat(cart.items()[0].unitPrice()).isEqualTo(Money(1.5f)) },
                { assertThat(cart.items()[0].quantity()).isEqualTo(Quantity(444)) }
        )
    }

    @Test
    fun multiple_items_are_added() {
        val cart: Cart = CartJdbc(CartId("CTEST"), jdbcTemplate)
        cart.add(CartItem(ProductId("PTEST"), Title("Test"), Money(1.5f), Quantity(123)))
        cart.add(CartItem(ProductId("PTEST"), Title("Test"), Money(2.5f), Quantity(123)))
        assertAll(
                { assertThat(cart.items()).hasSize(2) },
                { assertThat(cart.items()[0].productId()).isEqualTo(ProductId("PTEST")) },
                { assertThat(cart.items()[0].title()).isEqualTo(Title("Test")) },
                { assertThat(cart.items()[0].unitPrice()).isEqualTo(Money(1.5f)) },
                { assertThat(cart.items()[0].quantity()).isEqualTo(Quantity(123)) },
                { assertThat(cart.items()[1].productId()).isEqualTo(ProductId("PTEST")) },
                { assertThat(cart.items()[1].title()).isEqualTo(Title("Test")) },
                { assertThat(cart.items()[1].unitPrice()).isEqualTo(Money(2.5f)) },
                { assertThat(cart.items()[1].quantity()).isEqualTo(Quantity(123)) }
        )
    }

    @Test
    fun item_is_removed() {
        val cart: Cart = CartJdbc(CartId("C001"), jdbcTemplate)
        cart.remove(ProductId("P001"))
        assertAll(
                { assertThat(cart.hasItems()).isTrue() },
                { assertThat(cart.items()).hasSize(1) },
                { assertThat(cart.items()[0].productId()).isEqualTo(ProductId("P002")) },
                { assertThat(cart.items()[0].title()).isEqualTo(Title("Test 2")) },
                { assertThat(cart.items()[0].unitPrice()).isEqualTo(Money(10.5f)) },
                { assertThat(cart.items()[0].quantity()).isEqualTo(Quantity(2)) }
        )
    }

    @Test
    fun cart_is_emptied() {
        val cart: Cart = CartJdbc(CartId("C001"), jdbcTemplate)
        cart.empty()
        assertAll(
                { assertThat(cart.hasItems()).isFalse() },
                { assertThat(cart.items()).isEmpty() }
        )
    }
}
