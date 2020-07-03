package com.ttulka.ecommerce.sales.cart

/**
 * Cart ID domain primitive.
 */
class CartId(private val id: String) {

    init {
        require(id.isNotBlank()) { "ID cannot be empty!" }
    }

    fun value(): String = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as CartId).id
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "CartId($id)"
}