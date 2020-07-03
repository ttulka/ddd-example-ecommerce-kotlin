package com.ttulka.ecommerce.sales.cart

import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId

/**
 * Cart entity.
 */
interface Cart {

    fun id(): CartId

    fun items(): List<CartItem>

    fun hasItems(): Boolean

    fun add(toAdd: CartItem)

    fun remove(productId: ProductId)

    fun empty()
}