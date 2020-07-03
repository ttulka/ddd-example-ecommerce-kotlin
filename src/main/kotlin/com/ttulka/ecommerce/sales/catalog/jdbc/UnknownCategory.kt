package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.category.Title
import com.ttulka.ecommerce.sales.catalog.category.Uri

/**
 * Null object implementation for Category entity.
 */
internal class UnknownCategory : Category {

    override fun id(): CategoryId = CategoryId(0)

    override fun uri(): Uri = Uri("unknown")

    override fun title(): Title = Title("unknown category")

    override fun changeTitle(title: Title) {
        // do nothing
    }
}