package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Title

/**
 * Null object implementation for Product entity.
 */
internal class UnknownProduct : Product {

    override fun id(): ProductId = ProductId(0)

    override fun title(): Title = Title("unknown product")

    override fun description(): Description = Description("This product is not to be found.")

    override fun price(): Money = Money(0.0f)

    override fun changeTitle(title: Title) {
        // do nothing
    }

    override fun changeDescription(description: Description) {
        // do nothing
    }

    override fun changePrice(price: Money) {
        // do nothing
    }

    override fun putForSale() {
        // do nothing
    }

    override fun categorize(categoryId: CategoryId) {
        // do nothing
    }
}
