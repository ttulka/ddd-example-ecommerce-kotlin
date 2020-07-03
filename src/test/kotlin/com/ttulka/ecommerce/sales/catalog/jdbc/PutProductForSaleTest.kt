package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.jdbc.config.CatalogJdbcConfig
import com.ttulka.ecommerce.sales.catalog.product.Description
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.sales.catalog.product.ProductId
import com.ttulka.ecommerce.sales.catalog.product.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ContextConfiguration

@JdbcTest
@ContextConfiguration(classes = [CatalogJdbcConfig::class])
class PutProductForSaleTest(
        @Autowired val findProducts: FindProducts,
        @Autowired val jdbcTemplate: JdbcTemplate) {

    @Test
    fun product_put_for_sale_is_found() {
        val product: Product = ProductJdbc(
                ProductId("TEST"),
                Title("test"),
                Description("test"),
                Money(1f),
                jdbcTemplate
        )
        product.putForSale()

        val found = findProducts.byId(ProductId("TEST"))
        assertThat(found.id()).isEqualTo(ProductId("TEST"))
    }
}
