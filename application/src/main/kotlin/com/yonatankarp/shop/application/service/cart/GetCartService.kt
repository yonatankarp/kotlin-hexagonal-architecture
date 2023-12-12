package com.yonatankarp.shop.application.service.cart

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.application.port.usecase.cart.GetCartUseCase
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId

class GetCartService(
    private val cartRepository: CartRepository,
) : GetCartUseCase {
    override operator fun invoke(customerId: CustomerId) =
        cartRepository.findByCustomerId(customerId)
            ?: Cart(customerId)
}
