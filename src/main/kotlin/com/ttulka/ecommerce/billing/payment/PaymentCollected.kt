package com.ttulka.ecommerce.billing.payment

import com.ttulka.ecommerce.common.events.DomainEvent
import java.time.Instant

/**
 * Payment Collected domain event.
 */
data class PaymentCollected(
        val timestamp: Instant,
        val referenceId: String) : DomainEvent {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return referenceId == (other as PaymentCollected).referenceId
    }

    override fun hashCode() = referenceId.hashCode()
}