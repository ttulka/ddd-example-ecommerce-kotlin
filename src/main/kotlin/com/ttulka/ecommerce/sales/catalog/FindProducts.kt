package com.ttulka.ecommerce.sales.catalog

import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Products

/**
 * Find Products use-case.
 */
interface FindProducts {

    /**
     * Lists all products.
     *
     * @return all products
     */
    fun all(): Products

    /**
     * Finds a product by ID.
     *
     * @param id the product ID
     * @return the found product
     */
    fun byId(id: ProductId): Product
}