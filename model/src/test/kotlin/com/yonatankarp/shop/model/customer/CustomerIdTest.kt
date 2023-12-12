package com.yonatankarp.shop.model.customer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class CustomerIdTest {
    @ParameterizedTest
    @ValueSource(ints = [-100, -1, 0])
    fun `given a value less than 1 - new CustomerId - throws exception`(value: Int) {
        // Then
        assertThrows<IllegalArgumentException> {
            // When
            CustomerId(value)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 8_765, 2_000_000_000])
    fun `given a value greater than or equal to 1 - new CustomerId - succeeds`(value: Int) {
        // When
        val customerId = CustomerId(value)

        // Then
        assertEquals(value, customerId.value)
    }
}
