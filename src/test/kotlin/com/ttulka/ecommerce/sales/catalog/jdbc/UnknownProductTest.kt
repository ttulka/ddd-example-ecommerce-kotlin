package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.category.CategoryId
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class UnknownProductTest {
    
    @Test
    fun unknown_product_has_values() {
        val unknownProduct: Product = UnknownProduct()
        assertAll(
                { assertThat(unknownProduct.id()).isEqualTo(ProductId(0)) },
                { assertThat(unknownProduct.title()).isNotNull() },
                { assertThat(unknownProduct.description()).isNotNull() },
                { assertThat(unknownProduct.price()).isNotNull() }
        )
    }

    @Test
    fun unknown_product_has_a_zero_price() {
        val unknownProduct: Product = UnknownProduct()
        assertThat(unknownProduct.price()).isEqualTo(Money(0f))
    }

    @Test
    fun change_title_noop() {
        val unknownProduct: Product = UnknownProduct()
        val unknownTitle = unknownProduct.title()

        unknownProduct.changeTitle(Title("test"))

        assertThat(unknownProduct.title()).isEqualTo(unknownTitle)
    }

    @Test
    fun change_description_noop() {
        val unknownProduct: Product = UnknownProduct()
        val unknownDescription = unknownProduct.description()

        unknownProduct.changeDescription(Description("test"))

        assertThat(unknownProduct.description()).isEqualTo(unknownDescription)
    }

    @Test
    fun change_price_noop() {
        val unknownProduct: Product = UnknownProduct()
        val unknownMoney = unknownProduct.price()

        unknownProduct.changePrice(Money(1f))

        assertThat(unknownProduct.price()).isEqualTo(unknownMoney)
    }

    @Test
    fun put_for_sale_noop() {
        val unknownProduct: Product = UnknownProduct()
        unknownProduct.putForSale()
    }

    @Test
    fun categorize_noop() {
        val unknownProduct: Product = UnknownProduct()
        unknownProduct.categorize(CategoryId(123))
    }
}
