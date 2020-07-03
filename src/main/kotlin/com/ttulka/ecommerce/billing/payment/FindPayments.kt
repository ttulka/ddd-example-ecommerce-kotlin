package com.ttulka.ecommerce.billing.payment

/**
 * Find Payments use-case.
 */
interface FindPayments {

    /**
     * Finds all payments.
     *
     * @return all payments
     */
    fun all(): Payments
}