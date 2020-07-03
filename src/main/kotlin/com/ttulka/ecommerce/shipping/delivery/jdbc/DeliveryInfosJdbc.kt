package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.shipping.delivery.DeliveryId
import com.ttulka.ecommerce.shipping.delivery.DeliveryInfo
import com.ttulka.ecommerce.shipping.delivery.DeliveryInfos
import com.ttulka.ecommerce.shipping.delivery.OrderId
import org.springframework.jdbc.core.JdbcTemplate

/**
 * JDBC implementation of Delivery Infos collection.
 */
internal class DeliveryInfosJdbc(
        private val query: String,
        private val jdbcTemplate: JdbcTemplate) : DeliveryInfos {

    private fun deliveryInfosFromDb(): Iterable<DeliveryInfo> =
        jdbcTemplate.queryForList("$query ORDER BY 1")
            .map(::toDeliveryInfo)

    private fun toDeliveryInfo(entry: Map<String, Any>): DeliveryInfo =
        DeliveryInfo(
            DeliveryId(entry["id"]!!),
            OrderId(entry["orderId"]!!))

    override fun iterator(): Iterator<DeliveryInfo> = deliveryInfosFromDb().iterator()
}