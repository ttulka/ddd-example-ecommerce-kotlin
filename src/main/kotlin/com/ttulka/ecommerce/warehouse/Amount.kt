package com.ttulka.ecommerce.warehouse

/**
 * Warehouse In Stock Amount domain primitive.
 */
class Amount(private val amount: Int) {

    companion object {
        val ZERO = Amount(0)
    }

    init {
        require(amount >= 0) { "Amount cannot be less than zero!" }
    }

    fun value() = amount

    operator fun plus(addend: Amount) = Amount(amount + addend.amount)

    operator fun minus(addend: Amount) = Amount(amount - addend.amount)

    operator fun compareTo(other: Amount) = amount.compareTo(other.amount)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return amount == (other as Amount).amount
    }

    override fun hashCode() = amount.hashCode()

    override fun toString() = "Amount($amount)"
}