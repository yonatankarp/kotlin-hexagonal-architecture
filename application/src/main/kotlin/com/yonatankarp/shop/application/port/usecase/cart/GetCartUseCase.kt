package com.yonatankarp.shop.application.port.usecase.cart

import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId

/**
 * The customer should be able to retrieve their shopping cart, including the
 * products, their respective quantity, the total number of products, and the
 * total price.
 */
interface GetCartUseCase {
    fun getCart(customerId: CustomerId): Cart
}
