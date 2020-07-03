package com.ttulka.ecommerce.sales.order

import com.ttulka.ecommerce.common.events.DomainEvent
import java.time.Instant

/**
 * Order Placed domain event.
 */
data class OrderPlaced(
        val timestamp: Instant,
        val orderId: String,
        val items: Map<String, Int>,
        val total: Float) : DomainEvent {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return orderId == (other as OrderPlaced).orderId
    }

    override fun hashCode() = orderId.hashCode()
}
