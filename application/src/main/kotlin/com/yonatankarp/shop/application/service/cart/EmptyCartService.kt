package com.yonatankarp.shop.application.service.cart

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.application.port.usecase.cart.EmptyCartUseCase
import com.yonatankarp.shop.model.customer.CustomerId

class EmptyCartService(
    private val cartRepository: CartRepository,
) : EmptyCartUseCase {
    override fun emptyCart(customerId: CustomerId) {
        cartRepository.deleteByCustomerId(customerId)
    }
}
