package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.sales.catalog.FindProducts
import com.ttulka.ecommerce.sales.catalog.FindProductsFromCategory
import com.ttulka.ecommerce.sales.catalog.category.Uri
import com.ttulka.ecommerce.sales.catalog.product.Product
import com.ttulka.ecommerce.warehouse.InStock
import com.ttulka.ecommerce.warehouse.ProductId
import com.ttulka.ecommerce.warehouse.Warehouse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * Web controller for Catalog use-cases.
 */
@Controller
internal class CatalogController(
        private val products: FindProducts,
        private val fromCategory: FindProductsFromCategory,
        private val warehouse: Warehouse) {

    companion object {
        private const val MAX_RESULTS = 10
    }

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("products", products.all()
                .range(MAX_RESULTS).map(::toData))
        return "catalog"
    }

    @GetMapping("/category/{categoryUri}")
    fun category(@PathVariable categoryUri: String, model: Model): String {
        model.addAttribute("products", fromCategory.byUri(Uri(categoryUri))
                .range(MAX_RESULTS).map(::toData))
        return "catalog"
    }

    private fun toData(product: Product): Map<String, Any> = mapOf(
        "id" to product.id().value(),
        "title" to product.title().value(),
        "description" to product.description().value(),
        "price" to product.price().value(),
        "inStock" to inStock(product).amount().value())

    private fun inStock(product: Product): InStock =
        warehouse.leftInStock(ProductId(product.id().value()))
}