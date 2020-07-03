package com.ttulka.ecommerce

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

/**
 * Configuration for sample data.
 * <p>
 * Some sample data to be filled into the database.
 */
@Configuration
@ConditionalOnClass(name = ["org.h2.Driver"])
@Profile("!test")
internal class ExampleDataConfig {
    @Bean
    fun dataSource(): DataSource =
        EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("/schema.sql", "/example-data.sql")
                .build()
}