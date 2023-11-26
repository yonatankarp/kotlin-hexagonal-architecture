package com.yonatankarp.shop.model.money

import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.money.MoneyFixture.usDollars
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.Currency

class MoneyTest {
    @Test
    fun `given amount with an invalid scale, new Money throws IllegalArgumentException`() {
        val amountWithScale3 = BigDecimal.valueOf(12999, 3)

        assertThrows<IllegalArgumentException> {
            Money(EUR, amountWithScale3)
        }
    }

    @Test
    fun `given a Euro amount, adding a Dollar amount throws IllegalArgumentException`() {
        // Given
        val euros = euros(11, 99)
        val dollars = usDollars(11, 99)

        // Then
        assertThrows<IllegalArgumentException> {
            // When
            euros + dollars
        }
    }

    @Test
    fun `multiplying money by a scalar updates its amount correctly`() {
        // Given
        val originalMoney = usDollars(10, 0)
        val scalar = 2L

        // When
        val actualMoney = originalMoney * scalar

        // Then
        val expectedMoney = usDollars(20, 0)
        assertEquals(expectedMoney, actualMoney)
    }

    @Test
    fun `multiplying money is commutative`() {
        // Given
        val money = euros(4, 99)
        val factor = 2L

        // When
        val resultA = money * factor
        val resultB = factor * money

        // Then
        assertEquals(resultA, resultB)
    }

    @Test
    fun `adding two money amounts results in correct sum`() {
        // Given
        val moneyA = euros(15, 0)
        val moneyB = euros(10, 0)

        // When
        val actualSum = moneyA + moneyB

        // Then
        val expectedSum = euros(25, 0)
        assertEquals(expectedSum, actualSum)
    }

    @Test
    fun `adding money is commutative`() {
        // Given
        val moneyA = euros(4, 99)
        val moneyB = euros(10, 50)

        // When
        val sumAB = moneyA + moneyB
        val sumBA = moneyB + moneyA

        // Then
        assertEquals(sumAB, sumBA)
    }

    companion object {
        private val EUR = Currency.getInstance("EUR")
    }
}
