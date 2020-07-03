package com.ttulka.ecommerce.sales.catalog.product

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.category.CategoryId

/**
 * Product entity.
 */
interface Product {

    fun id(): ProductId

    fun title(): Title

    fun description(): Description

    fun price(): Money

    fun changeTitle(title: Title)

    fun changeDescription(description: Description)

    fun changePrice(price: Money)

    fun putForSale()

    fun categorize(categoryId: CategoryId)
}
