package com.ttulka.ecommerce.shipping.delivery

import com.ttulka.ecommerce.common.events.DomainEvent
import java.time.Instant

/**
 * Delivery Dispatched domain event.
 */
data class DeliveryDispatched(
        val timestamp: Instant,
        val orderId: String) : DomainEvent {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return orderId == (other as DeliveryDispatched).orderId
    }

    override fun hashCode() = orderId.hashCode()
}
