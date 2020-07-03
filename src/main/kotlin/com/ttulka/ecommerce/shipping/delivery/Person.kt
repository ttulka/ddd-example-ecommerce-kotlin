package com.ttulka.ecommerce.shipping.delivery

import java.util.regex.Pattern

/**
 * Delivery Person entity.
 */
class Person(private val name: String) {

    init {
        require(!name.isBlank()) { "Person cannot be empty!" }
        require(Pattern.matches("([A-Z][a-zA-Z]+)( [a-zA-Z]+)?( [A-Z][']?[a-zA-Z]+)+", name)) { "Person value is invalid!" }
    }

    fun value(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return name == (other as Person).name
    }

    override fun hashCode() = name.hashCode()

    override fun toString() = "Person($name)"
}