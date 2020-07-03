package com.ttulka.ecommerce.sales.cart.item

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity

/**
 * Cart Item entity.
 */
class CartItem(
        private val productId: ProductId,
        private val title: Title,
        private val unitPrice: Money,
        private val quantity: Quantity) {

    fun productId(): ProductId = productId

    fun title(): Title = title

    fun unitPrice(): Money = unitPrice

    fun quantity(): Quantity = quantity

    fun total(): Money = unitPrice * quantity.value()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CartItem
        if (productId != other.productId) return false
        if (title != other.title) return false
        if (unitPrice != other.unitPrice) return false
        return true
    }

    override fun hashCode(): Int {
        var result = productId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + unitPrice.hashCode()
        return result
    }

    override fun toString() = "CartItem($productId, $title, $unitPrice, $quantity)"
}
