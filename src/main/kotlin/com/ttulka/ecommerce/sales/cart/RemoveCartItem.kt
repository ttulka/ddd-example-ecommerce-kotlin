package com.ttulka.ecommerce.sales.cart

import com.ttulka.ecommerce.sales.cart.item.ProductId

/**
 * Remove Cart Item use-case.
 */
interface RemoveCartItem {

    /**
     * Removes the item from the cart.
     *
     * @param cartId    the cart ID
     * @param productId the product ID to remove
     */
    fun fromCart(cartId: CartId, productId: ProductId)
}
