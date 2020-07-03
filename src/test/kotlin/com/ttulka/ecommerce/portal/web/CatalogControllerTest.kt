package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.FindProductsFromCategory
import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.Products
import com.ttulka.ecommerce.sales.catalog.product.Title
import com.ttulka.ecommerce.warehouse.Amount
import com.ttulka.ecommerce.warehouse.InStock
import com.ttulka.ecommerce.warehouse.ProductId
import com.ttulka.ecommerce.warehouse.Warehouse
import org.hamcrest.Matchers.iterableWithSize
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ContextConfiguration(classes = [CatalogController::class, CatalogControllerTest.TestConfig::class])
class CatalogControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun index_has_products() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(model().attribute("products", iterableWithSize<Any>(2)))
    }

    @Test
    fun category_has_products() {
        mockMvc.perform(get("/category/test-category"))
                .andExpect(status().isOk)
                .andExpect(model().attribute("products", iterableWithSize<Any>(2)))
    }

    @Configuration
    class TestConfig {
        @Bean
        fun findProducts(): FindProducts = object : FindProducts {
            override fun all(): Products = testProducts()
            override fun byId(id: com.ttulka.ecommerce.sales.catalog.product.ProductId): Product =
                    Mockito.mock(Product::class.java)
        }

        @Bean
        fun findProductsFromCategory(): FindProductsFromCategory = object : FindProductsFromCategory {
            override fun byUri(categoryUri: Uri): Products = testProducts()
        }

        @Bean
        fun warehouse(): Warehouse = object : Warehouse {
            override fun leftInStock(productId: ProductId): InStock = InStock(Amount(1))
            override fun putIntoStock(productId: ProductId, amount: Amount) {}
        }

        private fun testProducts(): Products {
            val product = Mockito.mock(Product::class.java)
            Mockito.`when`(product.id()).thenReturn(com.ttulka.ecommerce.sales.catalog.product.ProductId("TEST"))
            Mockito.`when`(product.title()).thenReturn(Title("Test"))
            Mockito.`when`(product.description()).thenReturn(Description("Test"))
            Mockito.`when`(product.price()).thenReturn(Money(1f))

            val products = Mockito.mock(Products::class.java)
            Mockito.`when`(products.iterator()).thenReturn(listOf(product, product).iterator())
            Mockito.`when`(products.range(ArgumentMatchers.anyInt())).thenReturn(products)

            return products
        }
    }
}