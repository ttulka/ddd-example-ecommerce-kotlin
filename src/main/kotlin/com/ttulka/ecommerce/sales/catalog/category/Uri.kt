package com.ttulka.ecommerce.sales.catalog.category

import java.util.regex.Pattern

/**
 * Category URI domain primitive.
 */
class Uri(private val uri: String) {

    init {
        require(uri.isNotBlank()) { "URI cannot be empty!" }
        require(Pattern.matches("[a-z]([a-z0-9-]*[a-z0-9])?", uri)) { "URI value is invalid!" }
    }

    fun value(): String = uri

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return uri == (other as Uri).uri
    }

    override fun hashCode() = uri.hashCode()

    override fun toString() = "Uri($uri)"
}