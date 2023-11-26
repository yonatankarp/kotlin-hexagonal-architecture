package com.yonatankarp.shop.application.service.product

import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FindProductsServiceTest {
    private val testProduct1 = createTestProduct(euros(19, 99))
    private val testProduct2 = createTestProduct(euros(25, 99))

    private val productRepository = mockk<ProductRepository>(relaxed = true)
    private val findProductsService = FindProductsService(productRepository)

    @Test
    fun `given a search query - findByNameOrDescription returns the products - returned by the persistence port`() {
        // Given
        every { productRepository.findByNameOrDescription("one") } returns listOf(testProduct1)
        every { productRepository.findByNameOrDescription("two") } returns listOf(testProduct2)
        every { productRepository.findByNameOrDescription("one-two") } returns listOf(testProduct1, testProduct2)
        every { productRepository.findByNameOrDescription("empty") } returns listOf()

        // When
        // Then
        assertEquals(listOf(testProduct1), findProductsService.findByNameOrDescription("one"))
        assertEquals(listOf(testProduct2), findProductsService.findByNameOrDescription("two"))
        assertEquals(listOf(testProduct1, testProduct2), findProductsService.findByNameOrDescription("one-two"))
        assertTrue(findProductsService.findByNameOrDescription("empty").isEmpty())
    }

    @Test
    fun `given a too short search query - findByNameOrDescription - throws an exception`() {
        // Given
        val searchQuery = "x"

        // Then
        assertThrows<IllegalArgumentException> {
            // When
            findProductsService.findByNameOrDescription(searchQuery)
        }
    }
}
