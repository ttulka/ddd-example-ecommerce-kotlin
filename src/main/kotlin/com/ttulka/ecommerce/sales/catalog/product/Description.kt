package com.ttulka.ecommerce.sales.catalog.product

/**
 * Product Description domain primitive.
 */
class Description(private val description: String) {

    fun value(): String = description.trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return description == (other as Description).description
    }

    override fun hashCode()= description.hashCode()

    override fun toString() = "Description($description)"
}
