package com.ttulka.ecommerce.common.primitives

/**
 * Money domain primitive.
 */
class Money(private val money: Float) {

    companion object {
        val ZERO = Money(0f)
    }

    init {
        val maxValue = 1000000f

        require(money >= 0) { "Money cannot be less than zero." }
        require(money <= maxValue) { "Money cannot be greater than $maxValue." }
    }

    fun value(): Float = money

    operator fun plus(summand: Money) = Money(money + summand.value())

    operator fun times(factor: Int) = Money(money * factor)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return money == (other as Money).money
    }

    override fun hashCode() = money.hashCode()

    override fun toString() = "Money($money)"
}