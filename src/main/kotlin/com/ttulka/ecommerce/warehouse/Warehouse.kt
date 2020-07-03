package com.ttulka.ecommerce.warehouse

/**
 * Warehouse use-cases.
 */
interface Warehouse {

    /**
     * Returns stock details for a product.
     *
     * @param productId the ID of the product
     * @return the stock details
     */
    fun leftInStock(productId: ProductId): InStock

    /**
     * Puts product items into the stock.
     *
     * @param productId the ID of the product
     * @param amount    the amount of items
     */
    fun putIntoStock(productId: ProductId, amount: Amount)
}
