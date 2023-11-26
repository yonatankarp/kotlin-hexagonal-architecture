package com.yonatankarp.shop.application.service.cart

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.application.port.usecase.cart.AddToCartUseCase
import com.yonatankarp.shop.application.port.usecase.cart.ProductNotFoundException
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.product.ProductId

class AddToCartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : AddToCartUseCase {
    override fun addToCart(
        customerId: CustomerId,
        productId: ProductId,
        quantity: Int,
    ): Cart {
        require(quantity > 0) { "'quantity' must be greater than 0" }

        val product =
            productRepository.findById(productId)
                ?: throw ProductNotFoundException()

        val cart =
            cartRepository.findByCustomerId(customerId)
                ?: Cart(customerId)

        cart.addProduct(product, quantity)

        cartRepository.save(cart)

        return cart
    }
}
