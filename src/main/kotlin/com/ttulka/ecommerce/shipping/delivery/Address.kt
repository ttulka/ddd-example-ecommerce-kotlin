package com.ttulka.ecommerce.shipping.delivery

/**
 * Delivery Address entity.
 */
class Address(
        private val person: Person,
        private val place: Place) {

    fun person(): Person = person

    fun place(): Place = place

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Address
        if (person != other.person) return false
        if (place != other.place) return false
        return true
    }

    override fun hashCode(): Int {
        var result = person.hashCode()
        result = 31 * result + place.hashCode()
        return result
    }

    override fun toString() = "Address($person, $place)"
}
