package com.ttulka.ecommerce.warehouse.jdbc.config

import com.ttulka.ecommerce.common.events.EventPublisher
import com.ttulka.ecommerce.warehouse.Warehouse
import com.ttulka.ecommerce.warehouse.jdbc.GoodsFetchingJdbc
import com.ttulka.ecommerce.warehouse.jdbc.WarehouseJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for JDBC implementation for Warehouse service.
 */
@Configuration
internal class WarehouseJdbcConfig {

    @Bean
    fun warehouseJdbc(jdbcTemplate: JdbcTemplate) = WarehouseJdbc(jdbcTemplate)

    @Bean
    fun goodsFetchingJdbc(warehouse: Warehouse, jdbcTemplate: JdbcTemplate, eventPublisher: EventPublisher) =
        GoodsFetchingJdbc(warehouse, jdbcTemplate, eventPublisher)
}