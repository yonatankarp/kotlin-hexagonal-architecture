package com.yonatankarp.shop.application.port.usecase.cart

import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.cart.NotEnoughItemsInStockException
import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.product.ProductId

/**
 * The customer should be able to add a product in a certain quantity to their
 * shopping cart.
 */
interface AddToCartUseCase {
    @Throws(
        ProductNotFoundException::class,
        NotEnoughItemsInStockException::class,
    )
    fun addToCart(
        customerId: CustomerId,
        productId: ProductId,
        quantity: Int,
    ): Cart
}
