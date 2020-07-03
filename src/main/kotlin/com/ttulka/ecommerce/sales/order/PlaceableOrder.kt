package com.ttulka.ecommerce.sales.order

/**
 * Placeable Order entity.
 */
interface PlaceableOrder : Order {

    /**
     * @throws [OrderAlreadyPlacedException] when the order has already been placed
     */
    fun place()

    /**
     * OrderAlreadyPlacedException is thrown when an already placed Order is placed.
     */
    class OrderAlreadyPlacedException internal constructor() : IllegalStateException()
}
