package com.ttulka.ecommerce.portal.config

import com.ttulka.ecommerce.portal.PlaceOrderFromCart
import com.ttulka.ecommerce.portal.PrepareOrderDelivery
import com.ttulka.ecommerce.sales.order.PlaceOrder
import com.ttulka.ecommerce.shipping.delivery.PrepareDelivery
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration for Portal component.
 */
@Configuration
internal class PortalConfig {
    
    @Bean
    fun placeOrderFromCart(placeOrder: PlaceOrder) = PlaceOrderFromCart(placeOrder)

    @Bean
    fun prepareOrderDelivery(prepareDelivery: PrepareDelivery) = PrepareOrderDelivery(prepareDelivery)
}
