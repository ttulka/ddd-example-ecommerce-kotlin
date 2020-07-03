package com.ttulka.ecommerce.portal

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.portal.PlaceOrderFromCart.NoItemsToOrderException
import com.ttulka.ecommerce.portal.config.PortalConfig
import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId
import com.ttulka.ecommerce.sales.cart.item.Title
import com.ttulka.ecommerce.shipping.delivery.PrepareDelivery
import com.ttulka.ecommerce.warehouse.Warehouse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import java.util.*

@JdbcTest
@ContextConfiguration(classes = [PortalConfig::class, PlaceOrderFromCartTest.ServicesTestConfig::class])
@Sql(statements = ["INSERT INTO products VALUES ('1', 'Test 1', 'Test', 1.00), (2, 'Test 2', 'Test', 2.00);"])
class PlaceOrderFromCartTest(
        @Autowired val placeOrderFromCart: PlaceOrderFromCart) {

    @Test
    fun order_is_placed() {
        val cart = mock(Cart::class.java)
        val cartItem = CartItem(ProductId("TEST"), Title("Test"), Money(1f), Quantity(1))
        Mockito.`when`(cart.hasItems()).thenReturn(true)
        Mockito.`when`(cart.items()).thenReturn(listOf(cartItem))

        placeOrderFromCart.placeOrder(UUID.randomUUID(), cart)
    }

    @Test
    fun empty_cart_throws_an_exception() {
        assertThrows<NoItemsToOrderException> {
            placeOrderFromCart.placeOrder(UUID.randomUUID(), mock(Cart::class.java)) }
    }

    @Configuration
    @ComponentScan("com.ttulka.ecommerce.sales")
    internal class ServicesTestConfig {
        @MockBean
        private lateinit var prepareDelivery: PrepareDelivery

        @MockBean
        private lateinit var  warehouse: Warehouse

        @MockBean
        private lateinit var  eventPublisher: EventPublisher
    }
}
