package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.CartId
import com.ttulka.ecommerce.sales.cart.RetrieveCart
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId
import com.ttulka.ecommerce.sales.cart.item.Title
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
@ContextConfiguration(classes = [CartController::class, CartControllerTest.TestConfig::class])
class CartControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun index_shows_the_cart_items() {
        mockMvc.perform(
                get("/cart"))
                .andExpect(status().isOk)
                .andExpect(model().attribute("items", listOf(mapOf(
                    "id" to "test-1",
                    "title" to "Test",
                    "price" to 1f * 123,
                    "quantity" to 123))))
    }

    @Test
    fun item_is_added_into_the_cart() {
        mockMvc.perform(
                post("/cart")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .param("productId", "test-1")
                    .param("title", "Test")
                    .param("price", "10.5")
                    .param("quantity", "123"))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/cart"))
    }

    @Test
    fun item_is_removed_from_the_cart() {
        mockMvc.perform(
                get("/cart/remove")
                    .param("productId", "test-1"))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/cart"))
    }

    @Configuration
    class TestConfig {
        @Bean
        fun retrieveCart(): RetrieveCart = object : RetrieveCart {
            override fun byId(cartId: CartId): Cart {
                val cart = Mockito.mock(Cart::class.java)
                Mockito.`when`(cart.items()).thenReturn(listOf(
                        CartItem(ProductId("test-1"), Title("Test"), Money(1f), Quantity(123))))
                return cart
            }
        }
    }
}
