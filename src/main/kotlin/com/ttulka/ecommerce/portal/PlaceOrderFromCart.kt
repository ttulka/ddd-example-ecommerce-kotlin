package com.ttulka.ecommerce.portal

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.cart.Cart
import com.ttulka.ecommerce.sales.cart.item.CartItem
import com.ttulka.ecommerce.sales.order.OrderId
import com.ttulka.ecommerce.sales.order.PlaceOrder
import com.ttulka.ecommerce.sales.order.item.OrderItem
import com.ttulka.ecommerce.sales.order.item.ProductId
import java.util.*

/**
 * Place Order From Cart use-case.
 */
class PlaceOrderFromCart(private val placeOrder: PlaceOrder) {

    /**
     * Places a new order created from the cart.
     *
     * @param orderId the order ID value
     * @param cart    the cart
     */
    fun placeOrder(orderId: UUID, cart: Cart) {
        if (!cart.hasItems()) {
            throw NoItemsToOrderException()
        }
        // here a command message PlaceOrder could be sent for lower coupling
        placeOrder.place(
            OrderId(orderId),
            cart.items().map(::toOrderItem),
            cart.items().map(CartItem::total).reduce(Money::plus))
    }

    private fun toOrderItem(cartItem: CartItem): OrderItem =
        OrderItem(ProductId(cartItem.productId().value()), cartItem.quantity())

    /**
     * NoItemsToOrderException is thrown when there are no items in the cart to be ordered.
     */
    internal class NoItemsToOrderException : RuntimeException()
}
