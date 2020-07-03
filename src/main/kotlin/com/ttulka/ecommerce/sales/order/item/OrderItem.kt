package com.ttulka.ecommerce.sales.order.item

import com.ttulka.ecommerce.common.primitives.Quantity

/**
 * Order Item entity.
 */
class OrderItem(
        private val productId: ProductId,
        private val quantity: Quantity) {

    fun productId(): ProductId = productId

    fun quantity(): Quantity = quantity

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as OrderItem
        if (productId != other.productId) return false
        if (quantity != other.quantity) return false
        return true
    }

    override fun hashCode(): Int {
        var result = productId.hashCode()
        result = 31 * result + quantity.hashCode()
        return result
    }

    override fun toString(): String {
        return "OrderItem($productId, $quantity)"
    }
}
