package com.yonatankarp.shop.adapter.output.persistence

import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.COMPUTER_MONITOR
import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.LED_LIGHTS
import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.MONITOR_DESK_MOUNT
import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.model.product.ProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

abstract class AbstractProductRepositoryTest<T : ProductRepository> {
    protected abstract fun createProductRepository(): T

    @Test
    fun `given test products and a test product id - find by id - returns a test product`() {
        // Given
        val productId = COMPUTER_MONITOR.id
        val productRepository = createProductRepository()

        // When
        val product = productRepository.findById(productId)

        // Then
        assertEquals(COMPUTER_MONITOR, product)
    }

    @Test
    fun `given the id of a product not persisted - find by id - returns null`() {
        // Given
        val productId = ProductId("00000")
        val productRepository = createProductRepository()

        // When
        val product = productRepository.findById(productId)

        // Then
        assertNull(product)
    }

    @Test
    fun `given test products and a search query not matching and product - find by name or description - returns an empty list`() {
        // Given
        val query = "not matching any product"
        val productRepository = createProductRepository()

        // When
        val products = productRepository.findByNameOrDescription(query)

        // Then
        assertTrue(products.isEmpty())
    }

    @Test
    fun `given test products and a search query matching one product - find by name or description - returns that product`() {
        // Given
        val query = "lights"
        val productRepository = createProductRepository()

        // When
        val products = productRepository.findByNameOrDescription(query)

        // Then
        assertEquals(listOf(LED_LIGHTS), products)
    }

    @Test
    fun `given test products and a search query matching two products - find by name or description - returns those products`() {
        // Given
        val query = "monitor"
        val productRepository = createProductRepository()

        // When
        val products = productRepository.findByNameOrDescription(query)

        // Then
        assertEquals(setOf(COMPUTER_MONITOR, MONITOR_DESK_MOUNT), products.toSet())
    }
}
