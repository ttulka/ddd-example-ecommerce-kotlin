package com.ttulka.ecommerce.shipping.delivery

/**
 * Delivery Info entity.
 * <p>
 * Basic information about Delivery.
 */
class DeliveryInfo(
        private val deliveryId: DeliveryId,
        private val orderId: OrderId) {

    fun id(): DeliveryId = deliveryId

    fun orderId(): OrderId = orderId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return deliveryId == (other as DeliveryInfo).deliveryId
    }

    override fun hashCode() = deliveryId.hashCode()

    override fun toString() = "DeliveryInfo(deliveryId=$deliveryId, orderId=$orderId)"
}
