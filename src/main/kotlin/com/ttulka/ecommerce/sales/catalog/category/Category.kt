package com.ttulka.ecommerce.sales.catalog.category

/**
 * Category entity.
 */
interface Category {

    fun id(): CategoryId

    fun uri(): Uri

    fun title(): Title

    fun changeTitle(title: Title)
}