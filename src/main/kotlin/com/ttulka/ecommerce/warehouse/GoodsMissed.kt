package com.ttulka.ecommerce.warehouse

import com.ttulka.ecommerce.common.events.DomainEvent
import java.time.Instant

/**
 * Goods Missed domain event.
 * <p>
 * Raised when a product is missed in the stock (sold out) and requested to be fetched.
 * <p>
 * Some other service could take care of it (eg. notify a supplier).
 * <br>In the current workflow the delivery is dispatched even when something is missing. This will be just delivered later.
 */
data class GoodsMissed(
        val timestamp: Instant,
        val productCode: String,
        val amount: Int) : DomainEvent {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GoodsMissed
        if (productCode != other.productCode) return false
        if (amount != other.amount) return false
        return true
    }

    override fun hashCode(): Int {
        var result = productCode.hashCode()
        result = 31 * result + amount
        return result
    }
}