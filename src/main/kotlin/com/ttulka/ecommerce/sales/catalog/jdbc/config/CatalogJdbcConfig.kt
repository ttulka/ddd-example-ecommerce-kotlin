package com.ttulka.ecommerce.sales.catalog.jdbc.config

import com.ttulka.ecommerce.sales.catalog.jdbc.FindCategoriesJdbc
import com.ttulka.ecommerce.sales.catalog.jdbc.FindProductsFromCategoryJdbc
import com.ttulka.ecommerce.sales.catalog.jdbc.FindProductsJdbc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Configuration for JDBC implementation for Catalog service.
 */
@Configuration
internal class CatalogJdbcConfig {

    @Bean
    fun findCategoriesJdbc(jdbcTemplate: JdbcTemplate) = FindCategoriesJdbc(jdbcTemplate)

    @Bean
    fun findProductsJdbc(jdbcTemplate: JdbcTemplate) = FindProductsJdbc(jdbcTemplate)

    @Bean
    fun findProductsFromCategoryJdbc(jdbcTemplate: JdbcTemplate) = FindProductsFromCategoryJdbc(jdbcTemplate)
}