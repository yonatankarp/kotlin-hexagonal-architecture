package com.yonatankarp.shop.application.service.cart

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GetCartServiceTest {
    private val testCustomerId = CustomerId(61157)
    private val testProduct1 = createTestProduct(euros(19, 99))
    private val testProduct2 = createTestProduct(euros(25, 99))

    private val cartRepository = mockk<CartRepository>(relaxed = true)
    private val getCartService = GetCartService(cartRepository)

    @Test
    fun `given cart is persisted - getCart - returns persisted cart`() {
        // Given
        val persistedCart =
            Cart(testCustomerId)
                .apply {
                    addProduct(testProduct1, 1)
                    addProduct(testProduct2, 5)
                }
        every { cartRepository.findByCustomerId(testCustomerId) } returns persistedCart

        // When
        val cart = getCartService(testCustomerId)

        // Then
        assertEquals(persistedCart, cart)
    }

    @Test
    fun `given cart is not persisted - getCart - returns an empty cart`() {
        // Given
        every { cartRepository.findByCustomerId(testCustomerId) } returns null

        // When
        val cart = getCartService(testCustomerId)

        // Then
        assertTrue(cart.lineItems().isEmpty())
    }
}
