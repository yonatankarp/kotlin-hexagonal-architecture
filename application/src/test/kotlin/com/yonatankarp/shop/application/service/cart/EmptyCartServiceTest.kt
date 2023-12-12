package com.yonatankarp.shop.application.service.cart

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.model.customer.CustomerId
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class EmptyCartServiceTest {
    private val testCustomerId = CustomerId(61157)
    private val cartRepository = mockk<CartRepository>(relaxed = true)
    private val emptyCartService = EmptyCartService(cartRepository)

    @Test
    fun `empty cart invokes delete on the persistence port`() {
        // When
        emptyCartService(testCustomerId)

        // Then
        verify { cartRepository.deleteByCustomerId(testCustomerId) }
    }
}
