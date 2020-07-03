package com.ttulka.ecommerce.warehouse

/**
 * Warehouse To Fetch entity.
 */
class ToFetch(
        private val productId: ProductId,
        private val amount: Amount) {

    fun productId(): ProductId = productId

    fun amount(): Amount = amount

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ToFetch
        if (productId != other.productId) return false
        if (amount != other.amount) return false
        return true
    }

    override fun hashCode(): Int {
        var result = productId.hashCode()
        result = 31 * result + amount.hashCode()
        return result
    }

    override fun toString() = "ToFetch($productId, $amount)"
}
