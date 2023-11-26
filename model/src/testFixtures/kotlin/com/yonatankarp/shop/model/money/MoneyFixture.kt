package com.yonatankarp.shop.model.money

import java.util.Currency

object MoneyFixture {
    private val EUR = Currency.getInstance("EUR")
    private val USD = Currency.getInstance("USD")

    fun euros(
        euros: Int,
        cents: Int,
    ) = Money.of(EUR, euros.toLong(), cents.toLong())

    fun usDollars(
        dollars: Int,
        cents: Int,
    ) = Money.of(USD, dollars.toLong(), cents.toLong())
}
