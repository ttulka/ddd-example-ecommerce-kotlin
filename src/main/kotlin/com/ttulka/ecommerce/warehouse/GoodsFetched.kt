package com.ttulka.ecommerce.warehouse

import com.ttulka.ecommerce.common.events.DomainEvent
import java.time.Instant

/**
 * Goods Fetched domain event.
 */
data class GoodsFetched(
        val timestamp: Instant,
        val orderId: String) : DomainEvent {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return orderId == (other as GoodsFetched).orderId
    }

    override fun hashCode() = orderId.hashCode()
}