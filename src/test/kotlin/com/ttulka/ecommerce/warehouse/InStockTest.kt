package com.ttulka.ecommerce.warehouse

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InStockTest {

    @Test
    fun in_stock_value() {
        val inStock = InStock(Amount(123))
        assertThat(inStock.amount()).isEqualTo(Amount(123))
    }

    @Test
    fun in_stock_zero_value() {
        val inStock = InStock(Amount.ZERO)
        assertThat(inStock.amount()).isEqualTo(Amount.ZERO)
    }

    @Test
    fun in_stock_is_added() {
        val sum = InStock(Amount(1)).add(Amount(2))
        assertThat(sum).isEqualTo(InStock(Amount(3)))
    }

    @Test
    fun in_stock_is_removed() {
        val remaining = InStock(Amount(1)).remove(Amount(1))
        assertThat(remaining).isEqualTo(InStock(Amount(0)))
    }

    @Test
    fun in_stock_is_sold_out() {
        val inStock = InStock(Amount(0))
        assertThat(inStock.isSoldOut).isTrue()
    }

    @Test
    fun in_stock_is_not_sold_out() {
        val inStock = InStock(Amount(1))
        assertThat(inStock.isSoldOut).isFalse()
    }

    @Test
    fun has_enough_in_stock() {
        val inStock = InStock(Amount(1))
        assertThat(inStock.hasEnough(Amount(1))).isTrue()
    }

    @Test
    fun has_enough_not_in_stock() {
        val inStock = InStock(Amount(1))
        assertThat(inStock.hasEnough(Amount(2))).isFalse()
    }

    @Test
    fun needs_yet_some() {
        val inStock = InStock(Amount(1))
        assertThat(inStock.needsYet(Amount(2))).isEqualTo(Amount(1))
    }

    @Test
    fun needs_yet_no_more() {
        val inStock = InStock(Amount(1))
        assertThat(inStock.needsYet(Amount(1))).isEqualTo(Amount(0))
    }
}