package com.ttulka.ecommerce.warehouse.jdbc

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.warehouse.*
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import kotlin.math.min

/**
 * JDBC implementation for Fetching Goods use-cases.
 */
internal open class GoodsFetchingJdbc(
        private val warehouse: Warehouse,
        private val jdbcTemplate: JdbcTemplate,
        private val eventPublisher: EventPublisher) : FetchGoods, RemoveFetchedGoods {

    private val log = LoggerFactory.getLogger(GoodsFetchingJdbc::class.java)

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun fetchFromOrder(orderId: OrderId, toFetch: Collection<ToFetch>) {
        toFetch.forEach { fetch(it, orderId) }

        eventPublisher.raise(GoodsFetched(Instant.now(), orderId.value()))

        log.info("Goods fetched: {}", toFetch)
    }

    private fun fetch(item: ToFetch, orderId: OrderId) {
        val inStock = warehouse.leftInStock(item.productId())

        if (!inStock.hasEnough(item.amount())) {
            eventPublisher.raise(GoodsMissed(
                    Instant.now(), item.productId().value(), inStock.needsYet(item.amount()).value()))
        }
        if (!inStock.isSoldOut) {
            val amountToFetch = min(item.amount().value(), inStock.amount().value())
            jdbcTemplate.update(
                    "INSERT INTO fetched_products VALUES (?, ?, ?)",
                    item.productId().value(), amountToFetch, orderId.value())
            jdbcTemplate.update(
                    "UPDATE products_in_stock SET amount = amount - ? WHERE product_id = ?",
                    amountToFetch, item.productId().value())
        }
    }

    @Transactional
    override fun removeForOrder(orderId: OrderId) {
        jdbcTemplate.update(
                "DELETE FROM fetched_products WHERE order_id = ?",
                orderId.value())

        log.info("Fetched goods removed: {}", orderId)
    }
}
