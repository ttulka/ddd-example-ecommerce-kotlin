package com.ttulka.ecommerce.shipping.delivery

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * Dispatch Delivery use-case.
 */
open class DispatchDelivery(private val findDeliveries: FindDeliveries) {
    
    /**
     * Dispatches a delivery by the order ID.
     *
     * @param orderId the order ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    open fun byOrder(orderId: OrderId) {
        val delivery = findDeliveries.byOrder(orderId)
        delivery.dispatch()
    }
}
