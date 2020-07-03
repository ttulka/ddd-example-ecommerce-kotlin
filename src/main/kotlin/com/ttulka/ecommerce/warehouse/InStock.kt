package com.ttulka.ecommerce.warehouse

/**
 * Warehouse In Stock entity.
 */
class InStock(private val amount: Amount) {

    fun amount(): Amount = amount

    fun add(addend: Amount): InStock = InStock(amount + addend)

    fun remove(addend: Amount): InStock = InStock(amount - addend)

    fun hasEnough(amount: Amount): Boolean = this.amount >= amount

    fun needsYet(amount: Amount): Amount =
            if (this.amount > amount) Amount.ZERO else amount - this.amount

    val isSoldOut: Boolean
        get() = amount == Amount.ZERO

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return amount == (other as InStock).amount
    }

    override fun hashCode() = amount.hashCode()

    override fun toString() = "InStock(amount=$amount)"
}