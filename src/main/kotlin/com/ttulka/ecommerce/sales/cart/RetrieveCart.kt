package com.ttulka.ecommerce.sales.cart

/**
 * Retrieve Cart use-case.
 */
interface RetrieveCart {

    /**
     * Retrieve cart by ID.
     *
     * @param cartId the cart ID
     * @return the cart
     */
    fun byId(cartId: CartId): Cart
}
