package com.yonatankarp.shop.application.port.usecase.cart

import com.yonatankarp.shop.model.customer.CustomerId

/**
 * The customer should be able to empty their shopping cart.
 */
interface EmptyCartUseCase {
    fun emptyCart(customerId: CustomerId)
}
