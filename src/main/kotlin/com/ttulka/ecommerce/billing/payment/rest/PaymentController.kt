package com.ttulka.ecommerce.billing.payment.rest

import com.ttulka.ecommerce.billing.payment.FindPayments
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for Payment use-cases.
 */
@RestController
@RequestMapping("/payment")
internal class PaymentController(
        private val findPayments: FindPayments) {

    companion object {
        private const val MAX_RESULTS = 10
    }

    @GetMapping
    fun all(): List<Map<String, *>> {
        return findPayments.all().range(MAX_RESULTS).map {
            mapOf(
                "id" to it.id().value(),
                "referenceId" to it.referenceId().value(),
                "total" to it.total().value(),
                "collected" to it.isCollected)
        }
    }
}