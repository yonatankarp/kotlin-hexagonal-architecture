package com.yonatankarp.shop.model.money

import java.math.BigDecimal
import java.util.Currency

data class Money(
    val currency: Currency,
    val amount: BigDecimal,
) {
    init {
        require(amount.scale() <= currency.defaultFractionDigits) {
            "Scale of amount $amount is greater than the number of fraction digits used with currency $currency"
        }
    }

    operator fun plus(augend: Money): Money {
        require(currency == augend.currency) {
            "Currency ${augend.currency} of augend does not match this money's currency $currency"
        }

        return Money(currency, amount.add(augend.amount))
    }

    operator fun times(multiplicand: Long) = Money(currency, amount.multiply(BigDecimal.valueOf(multiplicand)))

    companion object {
        fun of(
            currency: Currency,
            mayor: Long,
            minor: Long,
        ): Money {
            val scale = currency.defaultFractionDigits
            return Money(
                currency,
                BigDecimal.valueOf(mayor).add(BigDecimal.valueOf(minor, scale)),
            )
        }
    }
}

operator fun Long.times(multiplicand: Money): Money = multiplicand * this
