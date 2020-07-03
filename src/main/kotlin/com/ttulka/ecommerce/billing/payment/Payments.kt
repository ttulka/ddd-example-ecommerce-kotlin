package com.ttulka.ecommerce.billing.payment

/**
 * Payments collection.
 */
interface Payments : Iterable<Payment> {

    fun range(start: Int, limit: Int): Payments

    fun range(limit: Int): Payments
}
