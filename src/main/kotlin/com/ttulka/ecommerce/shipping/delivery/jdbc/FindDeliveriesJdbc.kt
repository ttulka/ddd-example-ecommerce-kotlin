package com.ttulka.ecommerce.shipping.delivery.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.shipping.delivery.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

/**
 * JDBC implementation for Delivery use-cases.
 */
internal open class FindDeliveriesJdbc(
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : FindDeliveries {

    override fun all(): DeliveryInfos =
        DeliveryInfosJdbc("SELECT id, order_id orderId FROM deliveries", jdbcTemplate)

    @Transactional
    override fun byOrder(orderId: OrderId): Delivery =
        jdbcTemplate.queryForList(
        """SELECT id, order_id orderId, person, place, dd.delivery_id dispatched FROM deliveries
                LEFT JOIN dispatched_deliveries dd ON id = dd.delivery_id
                WHERE order_id = ?""", orderId.value()).firstOrNull()
            ?.let(::toDelivery)
            ?:UnknownDelivery()

    override fun isPrepared(orderId: OrderId): Boolean =
        jdbcTemplate.queryForObject(
        """SELECT COUNT(order_id) FROM deliveries
                WHERE order_id = ?""", Int::class.java, orderId.value()) > 0

    private fun toDelivery(delivery: Map<String, Any?>): Delivery =
        DeliveryJdbc(
            DeliveryId(delivery["id"]!!),
            OrderId(delivery["orderId"]!!),
            Address(
                    Person(delivery["person"] as String),
                    Place(delivery["place"] as String)),
            delivery["dispatched"] != null,
            jdbcTemplate, eventPublisher)
}
