package com.ttulka.ecommerce.shipping.dispatching.jdbc

import com.ttulka.ecommerce.shipping.delivery.DispatchDelivery
import com.ttulka.ecommerce.shipping.dispatching.DispatchingSaga
import com.ttulka.ecommerce.shipping.dispatching.OrderId
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.JdbcTemplate

internal class DispatchingSagaJdbc(
        private val dispatchDelivery: DispatchDelivery,
        private val jdbcTemplate: JdbcTemplate) : DispatchingSaga {

    private enum class State {
        PREPARED, ACCEPTED, FETCHED, PAID, DISPATCHED
    }

    private val log = LoggerFactory.getLogger(DispatchingSagaJdbc::class.java)

    override fun prepared(orderId: OrderId) {
        jdbcTemplate.update("INSERT INTO dispatching_saga VALUES (?, ?)", orderId.value(), State.PREPARED.name)
        attemptToDispatch(orderId)
    }

    override fun accepted(orderId: OrderId) {
        jdbcTemplate.update("INSERT INTO dispatching_saga VALUES (?, ?)", orderId.value(), State.ACCEPTED.name)
        attemptToDispatch(orderId)
    }

    override fun fetched(orderId: OrderId) {
        jdbcTemplate.update("INSERT INTO dispatching_saga VALUES (?, ?)", orderId.value(), State.FETCHED.name)
        attemptToDispatch(orderId)
    }

    override fun paid(orderId: OrderId) {
        jdbcTemplate.update("INSERT INTO dispatching_saga VALUES (?, ?)", orderId.value(), State.PAID.name)
        attemptToDispatch(orderId)
    }

    private fun attemptToDispatch(orderId: OrderId) {
        if (isReadyToDispatch(orderId)) {
            dispatch(orderId)
        }
    }

    private fun isReadyToDispatch(orderId: OrderId): Boolean =
        jdbcTemplate.queryForObject(
                "SELECT COUNT(state) FROM dispatching_saga WHERE order_id = ? AND state IN (?, ?, ?, ?)",
                Int::class.java, orderId.value(),
                State.PREPARED.name, State.ACCEPTED.name, State.FETCHED.name, State.PAID.name) == 4

    private fun dispatch(orderId: OrderId) {
        try {
            jdbcTemplate.update("INSERT INTO dispatching_saga VALUES (?, ?)", orderId.value(), State.DISPATCHED.name)

            // here a command message DispatchDelivery could be sent for lower coupling
            // a saga should not query or modify master data, only its private state
            // this is a shortcut where the saga is calling the Delivery service
            // better would be when the saga just sends a message
            dispatchDelivery.byOrder(com.ttulka.ecommerce.shipping.delivery.OrderId(orderId.value()))

        } catch (e: DataIntegrityViolationException) {
            // this could happen when multiple message come at once (in parallel)
            log.trace("Failed attempt to dispatch: $orderId", e)
        }
    }
}
