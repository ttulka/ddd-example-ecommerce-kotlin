package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.portal.web.config.PortalWebConfig
import com.ttulka.ecommerce.sales.catalog.FindCategories
import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.FindProductsFromCategory
import com.ttulka.ecommerce.sales.catalog.category.Categories
import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.Title
import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.product.Products
import com.ttulka.ecommerce.warehouse.Warehouse
import org.hamcrest.Matchers.iterableWithSize
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CatalogController::class)
@Import(PortalWebConfig::class)
class WebLayoutAdviceTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var findCategories: FindCategories

    @MockBean
    private lateinit var findProducts: FindProducts

    @MockBean
    private lateinit var findProductsFromCategory: FindProductsFromCategory

    @MockBean
    private lateinit var warehouse: Warehouse

    @Test
    fun categories_are_on_every_page() {
        val categories = testCategories()
        Mockito.`when`(findCategories.all()).thenReturn(categories)
        val products = testProducts()
        Mockito.`when`(findProducts.all()).thenReturn(products)

        mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(model().attribute("categories", iterableWithSize<Any>(2)))
    }

    private fun testCategories(): Categories {
        val categories = mock(Categories::class.java)
        val category = mock(Category::class.java)
        Mockito.`when`(category.uri()).thenReturn(Uri("test"))
        Mockito.`when`(category.title()).thenReturn(Title("Test"))
        Mockito.`when`(categories.iterator()).thenReturn(listOf(category, category).iterator())
        return categories
    }

    private fun testProducts(): Products {
        val products = mock(Products::class.java)
        Mockito.`when`(products.iterator()).thenReturn(iterator { })
        Mockito.`when`(products.range(ArgumentMatchers.anyInt())).thenReturn(products)
        return products
    }
}
