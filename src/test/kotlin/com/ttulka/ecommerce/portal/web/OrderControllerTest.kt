package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.portal.config.PortalConfig
import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.CartId
import com.ttulka.ecommerce.sales.cart.RetrieveCart
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId
import com.ttulka.ecommerce.sales.cart.item.Title
import com.ttulka.ecommerce.sales.order.PlaceOrder
import com.ttulka.ecommerce.shipping.delivery.PrepareDelivery
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class OrderControllerTest {

    @WebMvcTest
    @ContextConfiguration(classes = [OrderController::class, PortalConfig::class, TestCartConfig::class])
    @TestPropertySource(properties = ["test.cart.full=true"])
    @Nested
    inner class FullCartTestSuite(@Autowired val mockMvc: MockMvc) {

        @MockBean
        private lateinit var placeOrder: PlaceOrder

        @MockBean
        private lateinit var prepareDelivery: PrepareDelivery

        @Test
        fun index_shows_the_order_form_with_name_and_address_input_fields() {
            mockMvc.perform(
                get("/order"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("<form")))
                .andExpect(content().string(containsString("action=\"/order\"")))
                .andExpect(content().string(containsString("method=\"post\"")))
                .andExpect(content().string(containsString("name=\"name\"")))
                .andExpect(content().string(containsString("name=\"address\"")))
        }

        @Test
        fun order_is_placed() {
            mockMvc.perform(
                post("/order")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .param("name", "Test Name")
                    .param("address", "Test Address 123"))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/order/success"))
        }

        @Test
        fun delivery_is_prepared() {
            mockMvc.perform(
                post("/order")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .param("name", "Test Name")
                    .param("address", "Test Address 123"))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/order/success"))
        }

        @Test
        fun order_form_is_not_filled() {
            mockMvc.perform(
                post("/order").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/order/error?message=requires"))
        }

        @Test
        fun success_is_shown() {
            mockMvc.perform(get("/order/success"))
                .andExpect(status().isOk)
        }

        @Test
        fun error_message_is_shown() {
            mockMvc.perform(get("/order/error")
                    .param("message", "testmessage"))
                .andExpect(status().isOk)
                .andExpect(model().attribute("messageCode", "testmessage"))
        }
    }

    @WebMvcTest
    @ContextConfiguration(classes = [OrderController::class, PortalConfig::class, TestCartConfig::class])
    @TestPropertySource(properties = ["test.cart.empty=true"])
    @Nested
    inner class EmptyCartTestSuite(@Autowired val mockMvc: MockMvc) {

        @MockBean
        private lateinit var placeOrder: PlaceOrder

        @MockBean
        private lateinit var prepareDelivery: PrepareDelivery

        @Test
        fun error_is_shown_for_no_items() {
            mockMvc.perform(
                post("/order")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .param("name", "Test Name")
                    .param("address", "Test Address 123")
                    .requestAttr("request", MockHttpServletRequest()))
                .andExpect(status().is3xxRedirection)
                .andExpect(redirectedUrl("/order/error?message=noitems"))
        }
    }

    private class FakeCart(private val items: List<CartItem>) : Cart {
        override fun id(): CartId = CartId("TEST")
        override fun items(): List<CartItem> = items
        override fun hasItems(): Boolean = items.isNotEmpty()
        override fun add(toAdd: CartItem) {}
        override fun remove(productId: ProductId) {}
        override fun empty() {}
    }

    @Configuration
    private class TestCartConfig {
        @Bean
        @ConditionalOnProperty("test.cart.full")
        fun retrieveCart(): RetrieveCart = object : RetrieveCart {
            override fun byId(cartId: CartId): Cart = FakeCart(listOf(
                CartItem(ProductId("test-1"), Title("Test"), Money(1f), Quantity(123)))
            )
        }
        @Bean
        @ConditionalOnProperty("test.cart.empty")
        fun retrieveEmptyCart(): RetrieveCart = object : RetrieveCart {
            override fun byId(cartId: CartId): Cart = FakeCart(emptyList())
        }
    }
}
