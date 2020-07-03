package com.ttulka.ecommerce.warehouse.rest

import com.ttulka.ecommerce.warehouse.Amount
import com.ttulka.ecommerce.warehouse.InStock
import com.ttulka.ecommerce.warehouse.ProductId
import com.ttulka.ecommerce.warehouse.Warehouse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ContextConfiguration(classes = [WarehouseController::class, WarehouseControllerTest.TestConfig::class])
internal class WarehouseControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val warehouse: Warehouse) {

    @Test
    fun left_in_stock() {
        warehouse.putIntoStock(ProductId("test-1"), Amount(123))

        mockMvc.perform(get("/warehouse/stock/test-1"))
                .andExpect(status().isOk)
                .andExpect(content().string("123"))
    }

    @Configuration
    class TestConfig {
        @Bean
        fun fakeWarehouse() =
                object : Warehouse {
                    private val stock = mutableMapOf<ProductId, InStock>()

                    override fun leftInStock(productId: ProductId): InStock =
                            stock.getOrElse(productId) { InStock(Amount.ZERO) }

                    override fun putIntoStock(productId: ProductId, amount: Amount) {
                        stock[productId] = InStock(amount)
                    }
                }
    }
}
