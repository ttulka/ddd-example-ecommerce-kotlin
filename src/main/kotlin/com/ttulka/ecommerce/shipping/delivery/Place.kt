package com.ttulka.ecommerce.shipping.delivery

/**
 * Delivery Place domain primitive.
 */
class Place(private val place: String) {

    init {
        require(!place.isBlank()) { "Place cannot be empty!" }
    }

    fun value(): String = place.trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return place.trim() == (other as Place).place.trim()
    }

    override fun hashCode() = place.hashCode()

    override fun toString() = "Place($place)"
}