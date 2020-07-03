package com.ttulka.ecommerce.portal.web

import com.ttulka.ecommerce.sales.cart.CartId
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Retrieve and save Cart ID from/to HTTP cookies.
 */
internal class CartIdFromCookies(
        private val request: HttpServletRequest,
        private val response: HttpServletResponse) {

    companion object {
        private const val COOKIE_NAME = "CART_ID"
    }

    val cartId: CartId by lazy {
        val value = request.cookies?.let {
            request.cookies
                    .filter { COOKIE_NAME.equals(it.name, ignoreCase = true) }
                    .map(Cookie::getValue)
                    .first()
        } ?: UUID.randomUUID().toString()

        saveCookie(value)

        CartId(value)
    }

    private fun saveCookie(value: String) {
        response.addCookie(asCookie(COOKIE_NAME, value))
    }

    private fun asCookie(name: String, value: String): Cookie {
        val cookie = Cookie(name, value)
        cookie.path = "/"
        return cookie
    }
}
