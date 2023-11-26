package com.yonatankarp.shop.model.product

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProductIdTest {
    @ParameterizedTest
    @ValueSource(strings = ["", "   "])
    fun `constructor - throws IllegalArgumentException - when value is blank`(value: String) {
        // Then
        assertThrows<IllegalArgumentException> {
            // When
            ProductId(value)
        }
    }

    @Test
    fun `constructor creates ProductId successfully with non-blank value`() {
        // Given
        val nonBlankValue = "ABC123"

        // When
        val productId = ProductId(nonBlankValue)

        // Then
        assertEquals(nonBlankValue, productId.value)
    }
}
