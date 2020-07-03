package com.ttulka.ecommerce.warehouse

/**
 * Warehouse To Fetch Order ID domain primitive.
 */
class OrderId(private val id: Any) {

    init {
        require(id.toString().isNotBlank()) { "ID cannot be empty!" }
    }

    fun value(): String = id.toString().trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id.toString() == (other as OrderId).id.toString()
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "OrderId($id)"
}