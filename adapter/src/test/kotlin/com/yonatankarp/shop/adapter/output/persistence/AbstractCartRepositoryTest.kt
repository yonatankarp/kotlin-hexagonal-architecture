package com.yonatankarp.shop.adapter.output.persistence

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.cart.CartLineItem
import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

internal abstract class AbstractCartRepositoryTest<T : CartRepository, U : ProductRepository> {
    @BeforeEach
    fun initRepositories() {
        persistTestProducts()
    }

    protected abstract fun createCartRepository(): T

    protected abstract fun createProductRepository(): U

    private fun persistTestProducts() {
        val productRepository = createProductRepository()
        productRepository.save(TEST_PRODUCT_1)
        productRepository.save(TEST_PRODUCT_2)
    }

    @Test
    fun `given a customer id for which no cart is persisted - find by customer id - returns null`() {
        // Given
        val cartRepository = createCartRepository()
        val customerId = createUniqueCustomerId()

        // When
        val cart = cartRepository.findByCustomerId(customerId)

        // Then
        assertNull(cart)
    }

    @Test
    fun `given persisted cart with product - find by customer id - returns the appropriate cart`() {
        // Given
        val cartRepository = createCartRepository()

        val customerId = createUniqueCustomerId()

        val persistedCart = Cart(customerId)
        persistedCart.addProduct(TEST_PRODUCT_1, 1)
        cartRepository.save(persistedCart)

        // When
        val cart = cartRepository.findByCustomerId(customerId)

        // Then
        assertNotNull(cart)
        assertEquals(customerId, cart!!.customerId)
        assertEquals(1, cart.lineItems().size)
        assertEquals(TEST_PRODUCT_1, cart.lineItems().first().product)
        assertEquals(1, cart.lineItems().first().quantity)
    }

    @Test
    fun `given existing cart with product - and given a new cart for the same customer - save cart - overwrites the existing cart`() {
        // Given
        val cartRepository = createCartRepository()

        val customerId = createUniqueCustomerId()

        val existingCart = Cart(customerId)
        existingCart.addProduct(TEST_PRODUCT_1, 1)
        cartRepository.save(existingCart)

        val newCart = Cart(customerId)
        newCart.addProduct(TEST_PRODUCT_2, 2)
        cartRepository.save(newCart)

        // When
        val cart = cartRepository.findByCustomerId(customerId)

        // Then
        assertNotNull(cart)
        assertEquals(customerId, cart!!.customerId)
        assertEquals(1, cart.lineItems().size)
        assertEquals(TEST_PRODUCT_2, cart.lineItems().first().product)
        assertEquals(2, cart.lineItems().first().quantity)
    }

    fun `given existing cart with product - add product and save cart - updates the existing cart`() {
        // Given
        val cartRepository = createCartRepository()
        val customerId = createUniqueCustomerId()

        val existingCart = Cart(customerId)
        existingCart.addProduct(TEST_PRODUCT_1, 1)
        cartRepository.save(existingCart)

        val updatedCart =
            cartRepository.findByCustomerId(customerId)
                ?: fail("Cart not found for customer id $customerId")
        updatedCart.addProduct(TEST_PRODUCT_2, 2)
        cartRepository.save(updatedCart)

        // When
        val cart = cartRepository.findByCustomerId(customerId)

        // Then
        assertNotNull(cart)
        assertEquals(customerId, cart!!.customerId)
        assertEquals(
            setOf(TEST_PRODUCT_1, TEST_PRODUCT_2),
            cart.lineItems().map(CartLineItem::product).toSet(),
        )
    }

    @Test
    fun `given existing cart - delete by customer id - deletes the cart`() {
        // Given
        val cartRepository = createCartRepository()
        val customerId = createUniqueCustomerId()

        val existingCart = Cart(customerId)
        cartRepository.save(existingCart)

        assertNotNull(cartRepository.findByCustomerId(customerId))

        // When
        cartRepository.deleteByCustomerId(customerId)

        // Then
        assertNull(cartRepository.findByCustomerId(customerId))
    }

    @Test
    fun `given not existing cart - delete by customer id - does nothing`() {
        // Given
        val cartRepository = createCartRepository()
        val customerId = createUniqueCustomerId()

        assertNull(cartRepository.findByCustomerId(customerId))

        // When
        cartRepository.deleteByCustomerId(customerId)

        // Then
        assertNull(cartRepository.findByCustomerId(customerId))
    }

    companion object {
        private val TEST_PRODUCT_1 = createTestProduct(euros(19, 99))
        private val TEST_PRODUCT_2 = createTestProduct(euros(1, 49))

        private val CUSTOMER_ID_SEQUENCE_GENERATOR = AtomicInteger()

        private fun createUniqueCustomerId() = CustomerId(CUSTOMER_ID_SEQUENCE_GENERATOR.incrementAndGet())
    }
}
