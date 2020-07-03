package com.ttulka.ecommerce.sales.catalog.jdbc

import com.ttulka.ecommerce.common.primitives.Money
import com.ttulka.ecommerce.sales.catalog.product.*
import org.springframework.jdbc.core.JdbcTemplate
import java.math.BigDecimal

/**
 * JDBC implementation of Products collection.
 */
internal class ProductsJdbc(
        private val query: String,
        private val queryParams: Array<Any>,
        private val jdbcTemplate: JdbcTemplate) : Products {

    companion object {
        private const val UNLIMITED = 1000
    }

    private var start = 0
    private var limit = UNLIMITED

    constructor(query: String, queryParam: Any, jdbcTemplate: JdbcTemplate) : this(query, arrayOf(queryParam), jdbcTemplate)
    constructor(query: String, jdbcTemplate: JdbcTemplate) : this(query, emptyArray(), jdbcTemplate)

    override fun range(start: Int, limit: Int): Products {
        require(!(start < 0 || limit <= 0 || limit - start > UNLIMITED)) {
            "Start must be greater than zero, " +
                    "items count must be greater than zero and less or equal than " + UNLIMITED
        }
        this.start = start
        this.limit = limit
        return this
    }

    override fun range(limit: Int): Products = range(0, limit)

    private fun productsFromDb(): Iterable<Product> {
        val params = queryParams.toMutableList()
        params.add(start)
        params.add(limit)
        return jdbcTemplate.queryForList("$query ORDER BY 1 LIMIT ?,?", *params.toTypedArray())
                .map(::toProduct)
    }

    private fun toProduct(entry: Map<String, Any>): Product =
        ProductJdbc(
            ProductId(entry["id"]!!),
            Title(entry["title"] as String),
            Description(entry["description"] as String),
            Money((entry["price"] as BigDecimal).toFloat()),
            jdbcTemplate)

    override fun iterator(): Iterator<Product> = productsFromDb().iterator()
}
