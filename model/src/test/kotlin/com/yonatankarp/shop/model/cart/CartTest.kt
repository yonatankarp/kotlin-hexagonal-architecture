package com.yonatankarp.shop.model.cart

import com.yonatankarp.shop.model.cart.CartFixture.emptyCartForRandomCustomer
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CartTest {
    @Test
    fun `given empty cart - adding two products - products are in cart`() {
        // Given
        val cart = emptyCartForRandomCustomer()
        val product1 = createTestProduct(euros(12, 99))
        val product2 = createTestProduct(euros(5, 97))

        // When
        cart.addProduct(product1, 3)
        cart.addProduct(product2, 5)

        // Then
        val lineItems = cart.lineItems()
        assertEquals(2, lineItems.size)

        assertEquals(product1, lineItems[0].product)
        assertEquals(3, lineItems[0].quantity)

        assertEquals(product2, lineItems[1].product)
        assertEquals(5, lineItems[1].quantity)
    }

    @Test
    fun `given empty cart - adding two products - numberOfItems and subTotal are calculated correctly`() {
        // Given
        val cart = emptyCartForRandomCustomer()
        val product1 = createTestProduct(euros(12, 99))
        val product2 = createTestProduct(euros(5, 97))

        // When
        cart.addProduct(product1, 3)
        cart.addProduct(product2, 5)

        // Then
        assertEquals(8, cart.numberOfItems())
        assertEquals(euros(68, 82), cart.subTotal())
    }

    @Test
    fun `given empty cart - numberOfItems and subTotal are calculated correctly`() {
        // Given
        val cart = emptyCartForRandomCustomer()

        // Then
        assertEquals(0, cart.numberOfItems())
        assertNull(cart.subTotal())
    }

    @Test
    fun `given a product with a few items available - adding more items than available to the cart - throws exception`() {
        // Given
        val cart = emptyCartForRandomCustomer()
        val product = createTestProduct(euros(9, 97), 3)

        // When
        val exception =
            assertThrows<NotEnoughItemsInStockException> {
                cart.addProduct(product, 4)
            }

        // Then
        assertEquals(product.itemsInStock, exception.itemsInStock)
    }

    @Test
    fun `given a product with a few items available - adding all available items to the cart - succeeds`() {
        // Given
        val cart = emptyCartForRandomCustomer()
        val product = createTestProduct(euros(9, 97), 3)

        // Then
        assertDoesNotThrow {
            // When
            cart.addProduct(product, 3)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [-100, -1, 0])
    fun `given empty cart - adding less than one item of a product - throws exception`(quantity: Int) {
        // Given
        val cart = emptyCartForRandomCustomer()
        val product = createTestProduct(euros(1, 49))

        // Then
        assertThrows<IllegalArgumentException> {
            // When
            cart.addProduct(product, quantity)
        }
    }
}
