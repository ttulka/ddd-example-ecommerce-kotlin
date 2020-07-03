package com.ttulka.ecommerce.shipping.delivery.rest

import com.ttulka.ecommerce.shipping.delivery.*
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ContextConfiguration(classes = [DeliveryController::class, DeliveryControllerTest.TestConfig::class])
internal class DeliveryControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun all_deliveries() {
        mockMvc.perform(get("/delivery"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", Matchers.hasSize<Any>(1)))
                .andExpect(jsonPath("$[0].id", Matchers.`is`("D1")))
                .andExpect(jsonPath("$[0].orderId", Matchers.`is`("O1")))
    }

    @Test
    fun delivery_by_order() {
        mockMvc.perform(get("/delivery/order/O1"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", Matchers.`is`("D1")))
                .andExpect(jsonPath("$.address.person", Matchers.`is`("Test Person")))
                .andExpect(jsonPath("$.address.place", Matchers.`is`("Test Place 123")))
                .andExpect(jsonPath("$.dispatched", Matchers.`is`(false)))
    }

    @Configuration
    class TestConfig {
        @Bean
        fun findDeliveries(): FindDeliveries = object : FindDeliveries {
            override fun all(): DeliveryInfos = object : DeliveryInfos {
                override fun iterator(): Iterator<DeliveryInfo> = listOf(
                        DeliveryInfo(DeliveryId("D1"), OrderId("O1"))
                ).iterator()
            }

            override fun byOrder(orderId: OrderId): Delivery = object : Delivery {
                override fun id(): DeliveryId = DeliveryId("D1")
                override fun orderId(): OrderId = OrderId("O1")
                override fun address(): Address = Address(Person("Test Person"), Place("Test Place 123"))
                override fun prepare() {}
                override fun dispatch() {}
                override val isDispatched: Boolean = false
            }

            override fun isPrepared(orderId: OrderId): Boolean = true
        }
    }
}