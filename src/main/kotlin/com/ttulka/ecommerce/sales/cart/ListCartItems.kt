package com.ttulka.ecommerce.sales.cart

import com.ttulka.ecommerce.sales.cart.item.CartItem

/**
 * List Cart Items use-case.
 */
interface ListCartItems {

    /**
     * Lists items in the cart.
     *
     * @param cartId the cart ID
     * @return items in the cart
     */
    fun listCart(cartId: CartId): List<CartItem>
}
