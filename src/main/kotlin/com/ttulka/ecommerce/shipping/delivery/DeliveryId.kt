package com.ttulka.ecommerce.shipping.delivery

/**
 * Delivery ID domain primitive.
 */
class DeliveryId(private val id: Any) {

    init {
        require(id.toString().isNotBlank()) { "ID cannot be empty!" }
    }

    fun value(): String = id.toString().trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id.toString() == (other as DeliveryId).id.toString()
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "DeliveryId($id)"
}