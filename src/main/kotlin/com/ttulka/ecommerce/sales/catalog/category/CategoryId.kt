package com.ttulka.ecommerce.sales.catalog.category

/**
 * Category ID domain primitive.
 */
class CategoryId(private val id: Any) {

    init {
        require(id.toString().isNotBlank()) { "ID cannot be empty!" }
    }

    fun value(): String = id.toString().trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id.toString() == (other as CategoryId).id.toString()
    }

    override fun hashCode() = id.hashCode()

    override fun toString() = "CategoryId($id)"
}