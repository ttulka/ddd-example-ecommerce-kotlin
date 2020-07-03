package com.ttulka.ecommerce.shipping.delivery.rest

import com.ttulka.ecommerce.shipping.delivery.FindDeliveries
import com.ttulka.ecommerce.shipping.delivery.OrderId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for Delivery use-cases.
 */
@RestController
@RequestMapping("/delivery")
internal class DeliveryController(
        private val findDeliveries: FindDeliveries) {

    @GetMapping
    fun all(): List<Map<String, *>> =
        findDeliveries.all().map {
            mapOf(
                "id" to it.id().value(),
                "orderId" to it.orderId().value())
        }

    @GetMapping("/order/{orderId}")
    fun byOrder(@PathVariable orderId: Any): Map<String, *> {
        val delivery = findDeliveries.byOrder(OrderId(orderId))
        return mapOf(
            "id" to delivery.id().value(),
            "address" to mapOf(
                "person" to delivery.address().person().value(),
                "place" to delivery.address().place().value()),
            "dispatched" to delivery.isDispatched)
    }
}