package com.ttulka.ecommerce

import io.restassured.RestAssured
import io.restassured.filter.cookie.CookieFilter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql("/test-data-order-workflow.sql")
class OrderWorkFlowTest {

    @LocalServerPort
    private val port = 0

    @Test
    fun order_is_shipped() {
        val cookieFilter = CookieFilter() // share cookies among requests
        RestAssured.with() // add an cart item
                .filter(cookieFilter)
                .port(port)
                .basePath("/cart")
                .param("productId", "p-1")
                .param("title", "Prod 1")
                .param("price", 1f)
                .param("quantity", 1)
                .post()
                .andReturn()

        RestAssured.with() // place an order
                .filter(cookieFilter)
                .port(port)
                .basePath("/order")
                .formParam("name", "Test Name")
                .formParam("address", "Test Address 123")
                .post()
                .andReturn()

        // delivery is dispatched

        Thread.sleep(120) // wait for all message to come

        val orderId = RestAssured.with().log().ifValidationFails()
                .port(port)
                .basePath("/delivery")
                .get()
                .andReturn()
                .jsonPath().getMap<Any, Any>("[0]")["orderId"]

        assertThat(orderId).isNotNull.`as`("No delivery found for a new order.")

        val deliveryJson = RestAssured.with().log().ifValidationFails()
                .port(port)
                .basePath("/delivery")["/order/$orderId"]
                .andReturn()
                .jsonPath()

        assertAll(
                { assertThat(deliveryJson.getBoolean("dispatched")).isTrue().`as`("Delivery is not dispatched.") },
                { assertThat(deliveryJson.getMap<String, String>("address")["person"]).isEqualTo("Test Name") },
                { assertThat(deliveryJson.getMap<String, String>("address")["place"]).isEqualTo("Test Address 123") })
    }

    @Test
    fun dispatched_items_are_removed_from_stock() {
        val cookieFilter = CookieFilter() // share cookies among requests

        RestAssured.with() // add an cart item
                .filter(cookieFilter)
                .port(port)
                .basePath("/cart")
                .param("productId", "p-2")
                .param("title", "Prod 2")
                .param("price", 2f)
                .param("quantity", 123)
                .post()
                .andReturn()

        RestAssured.with() // place an order
                .filter(cookieFilter)
                .port(port)
                .basePath("/order")
                .formParam("name", "Test Name")
                .formParam("address", "Test Address 123")
                .post()
                .andReturn()

        Thread.sleep(120) // wait for all message to come

        // (1000-123) left in stock
        val leftInStock = RestAssured.with().log().ifValidationFails()
                .port(port)
                .basePath("/warehouse/stock")["p-2"]
                .andReturn()
                .body().print()

        assertThat(leftInStock).isEqualTo((1000 - 123).toString()).`as`("Items are not removed from the stock.")
    }

    @Test
    fun payment_for_an_order_is_collected() {
        val cookieFilter = CookieFilter() // share cookies among requests

        RestAssured.with() // add an cart item
                .filter(cookieFilter)
                .port(port)
                .basePath("/cart")
                .param("productId", "p-3")
                .param("title", "Prod 3")
                .param("price", 3.50f)
                .param("quantity", 3)
                .post()
                .andReturn()

        RestAssured.with() // place an order
                .filter(cookieFilter)
                .port(port)
                .basePath("/order")
                .formParam("name", "Test Name")
                .formParam("address", "Test Address 123")
                .post()
                .andReturn()

        Thread.sleep(120) // wait for all message to come

        // payment is collected
        val payment = RestAssured.with().log().ifValidationFails()
                .port(port)
                .basePath("/payment")
                .get()
                .andReturn()
                .jsonPath().getMap<String, Any>("[0]")

        assertAll(
                { assertThat(payment["collected"]).isEqualTo(true).`as`("Payment is not collected.") },
                { assertThat(payment["total"]).isEqualTo(10.5f).`as`("Payment does not match.") })
    }
}