package com.ttulka.ecommerce.billing.payment

import com.ttulka.ecommerce.common.primitives.Money

/**
 * Collect Payment use-case.
 */
interface CollectPayment {

    /**
     * Collects a payment.
     *
     * @param referenceId the reference ID for the payment
     * @param total       the total amount of money to be collected
     */
    fun collect(referenceId: ReferenceId, total: Money)
}
