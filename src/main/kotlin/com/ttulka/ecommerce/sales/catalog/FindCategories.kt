package com.ttulka.ecommerce.sales.catalog

import com.ttulka.ecommerce.sales.catalog.category.Categories
import com.ttulka.ecommerce.sales.catalog.category.Category
import com.ttulka.ecommerce.sales.catalog.category.CategoryId

/**
 * Find Categories use-case.
 */
interface FindCategories {

    /**
     * Lists all categories.
     *
     * @return all categories
     */
    fun all(): Categories

    /**
     * Finds a category by the category ID.
     *
     * @param id the category ID
     * @return the category
     */
    fun byId(id: CategoryId): Category
}