package com.ttulka.ecommerce.sales.catalog

import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.product.Products

/**
 * Find Products from Category use-case.
 */
interface FindProductsFromCategory {
    /**
     * Lists products from the category by URI
     *
     * @param categoryUri the URI of the category
     * @return the products from the category
     */
    fun byUri(categoryUri: Uri): Products
}