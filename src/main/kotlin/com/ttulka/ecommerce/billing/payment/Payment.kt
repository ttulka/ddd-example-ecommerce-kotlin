package com.ttulka.ecommerce.billing.payment

import com.ttulka.ecommerce.common.primitives.Money

/**
 * Payment entity.
 */
interface Payment {

    fun id(): PaymentId

    fun referenceId(): ReferenceId

    fun total(): Money

    /**
     * @throws [PaymentAlreadyRequestedException] when the payment has already been requested
     */
    fun request()

    /**
     * @throws [PaymentNotRequestedYetException] when the payment has not been requested yet
     * @throws [PaymentAlreadyCollectedException] when the payment has already collected
     */
    fun collect()

    val isRequested: Boolean

    val isCollected: Boolean

    /**
     * PaymentAlreadyRequestedException is thrown when an already requested Payment is requested.
     */
    class PaymentAlreadyRequestedException internal constructor() : IllegalStateException()

    /**
     * PaymentNotRequestedYetException is thrown when a Payment is collected but not requested yet.
     */
    class PaymentNotRequestedYetException internal constructor() : IllegalStateException()

    /**
     * PaymentAlreadyCollectedException is thrown when an already collected Payment is collected.
     */
    class PaymentAlreadyCollectedException internal constructor() : IllegalStateException()
}
