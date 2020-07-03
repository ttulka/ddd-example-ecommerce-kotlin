package com.ttulka.ecommerce.sales.catalog.product

/**
 * Products collection.
 */
interface Products : Iterable<Product> {

    fun range(start: Int, limit: Int): Products

    fun range(limit: Int): Products
}
