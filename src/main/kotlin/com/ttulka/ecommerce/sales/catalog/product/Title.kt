package com.ttulka.ecommerce.sales.catalog.product

/**
 * Product Title domain primitive.
 */
class Title(private val title: String) {

    init {
        require(title.isNotBlank()) { "Title cannot be empty!" }
    }

    fun value(): String = title.trim()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return title == (other as Title).title
    }

    override fun hashCode() = title.hashCode()

    override fun toString() = "Title($title)"
}