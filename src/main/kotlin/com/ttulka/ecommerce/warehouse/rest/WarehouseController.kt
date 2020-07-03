package com.ttulka.ecommerce.warehouse.rest

import com.ttulka.ecommerce.warehouse.ProductId
import com.ttulka.ecommerce.warehouse.Warehouse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for Warehouse use-cases.
 */
@RestController
@RequestMapping("/warehouse")
internal class WarehouseController(
        private val warehouse: Warehouse) {

    @GetMapping("/stock/{productId}")
    fun productsInStock(@PathVariable productId: String): Int {
        return warehouse.leftInStock(ProductId(productId)).amount().value()
    }
}