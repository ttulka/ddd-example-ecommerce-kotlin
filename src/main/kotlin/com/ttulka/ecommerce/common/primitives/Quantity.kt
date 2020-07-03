package com.ttulka.ecommerce.common.primitives

/**
 * Quantity domain primitive.
 */
class Quantity(private val quantity: Int) {

    companion object {
        val ZERO = Quantity(0)
        val ONE = Quantity(1)
    }

    init {
        require(quantity >= 0) { "Quantity cannot be less than zero!" }
    }

    fun value(): Int = quantity

    operator fun plus(addend: Quantity) = Quantity(quantity + addend.value())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return quantity == (other as Quantity).quantity
    }

    override fun hashCode() = quantity.hashCode()

    override fun toString() = "Quantity($quantity)"
}