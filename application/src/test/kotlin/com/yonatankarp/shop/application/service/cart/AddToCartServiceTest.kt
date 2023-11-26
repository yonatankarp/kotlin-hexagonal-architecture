package com.yonatankarp.shop.application.service.cart

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.application.port.usecase.cart.ProductNotFoundException
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import com.yonatankarp.shop.model.product.ProductId
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class AddToCartServiceTest {
    private val testCustomerId = CustomerId(61157)
    private val testProduct1 = createTestProduct(euros(19, 99))
    private val testProduct2 = createTestProduct(euros(25, 99))

    private val cartRepository = mockk<CartRepository>(relaxed = true)
    private val productRepository = mockk<ProductRepository>(relaxed = true)
    private val addToCartService =
        AddToCartService(cartRepository, productRepository)

    @BeforeEach
    fun setUp() {
        every { productRepository.findById(testProduct1.id) } returns testProduct1
        every { productRepository.findById(testProduct2.id) } returns testProduct2
    }

    @Test
    fun `given existing cart - add to cart - cart with added product is saved and returned`() {
        // Given
        val persistedCart =
            Cart(testCustomerId)
                .apply { addProduct(testProduct1, 1) }
        every { cartRepository.findByCustomerId(testCustomerId) } returns persistedCart

        // When
        val cart =
            addToCartService.addToCart(testCustomerId, testProduct2.id, 3)

        // Then
        verify { cartRepository.save(cart) }

        val lineItems = cart.lineItems()
        assertEquals(2, lineItems.size)

        assertEquals(testProduct1, lineItems[0].product)
        assertEquals(1, lineItems[0].quantity)

        assertEquals(testProduct2, lineItems[1].product)
        assertEquals(3, lineItems[1].quantity)
    }

    @Test
    fun `given no existing cart - add to cart - cart with added product is saved and returned`() {
        // Given
        every { cartRepository.findByCustomerId(testCustomerId) } returns null

        // When
        val cart =
            addToCartService.addToCart(
                customerId = testCustomerId,
                productId = testProduct1.id,
                quantity = 2,
            )

        // Then
        verify { cartRepository.save(cart) }

        val lineItems = cart.lineItems()
        assertEquals(1, lineItems.size)
        assertEquals(testProduct1, lineItems[0].product)
        assertEquals(2, lineItems[0].quantity)
    }

    @Test
    fun `given an unknown product id - add to cart - throws exception`() {
        // Given
        val productId = ProductId.randomProductId()
        every { productRepository.findById(productId) } returns null

        // When
        assertThrows<ProductNotFoundException> {
            addToCartService.addToCart(testCustomerId, productId, 1)
        }

        // Then
        verify(exactly = 0) { cartRepository.save(any()) }
    }

    @Test
    fun `given quantity less than 1 - add to cart - throws exception`() {
        // Given
        val quantity = 0

        // When
        assertThrows<IllegalArgumentException> {
            addToCartService.addToCart(
                testCustomerId,
                testProduct1.id,
                quantity,
            )
        }

        // Then
        verify(exactly = 0) { cartRepository.save(any()) }
    }
}
