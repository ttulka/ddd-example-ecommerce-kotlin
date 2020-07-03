package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.common.primitives.Quantity
import com.ttulka.ecommerce.sales.cart.RetrieveCart
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.cart.item.ProductId
import com.ttulka.ecommerce.sales.cart.item.Title
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Web controller for Cart use-cases.
 */
@Controller
@RequestMapping("/cart")
internal class CartController(private val retrieveCart: RetrieveCart) {

    @GetMapping
    fun index(model: Model, request: HttpServletRequest, response: HttpServletResponse): String {
        val cartId = CartIdFromCookies(request, response).cartId
        model.addAttribute(
            "items",
            retrieveCart.byId(cartId).items().map {
                mapOf(
                    "id" to it.productId().value(),
                    "title" to it.title().value(),
                    "price" to it.total().value(),
                    "quantity" to it.quantity().value())
            })
        return "cart"
    }

    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun add(productId: String, title: String, price: Float, quantity: Int,
            request: HttpServletRequest, response: HttpServletResponse): String {
        val cartId = CartIdFromCookies(request, response).cartId
        retrieveCart.byId(cartId).add(CartItem(
                ProductId(productId),
                Title(title),
                Money(price),
                Quantity(quantity)))
        return "redirect:/cart"
    }

    @GetMapping("/remove")
    fun remove(productId: String,
               request: HttpServletRequest, response: HttpServletResponse): String {
        val cartId = CartIdFromCookies(request, response).cartId
        retrieveCart.byId(cartId).remove(ProductId(productId))
        return "redirect:/cart"
    }
}
