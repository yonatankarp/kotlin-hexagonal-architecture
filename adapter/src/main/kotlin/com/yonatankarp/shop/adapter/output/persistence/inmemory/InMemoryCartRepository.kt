package com.yonatankarp.shop.adapter.output.persistence.inmemory

import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId
import java.util.concurrent.ConcurrentHashMap

class InMemoryCartRepository(
    private val carts: MutableMap<CustomerId, Cart> = ConcurrentHashMap<CustomerId, Cart>(),
) : CartRepository {
    override fun save(cart: Cart) {
        carts[cart.customerId] = cart
    }

    override fun findByCustomerId(customerId: CustomerId): Cart? = carts.getOrElse(customerId) { null }

    override fun deleteByCustomerId(customerId: CustomerId) {
        carts.remove(customerId)
    }
}
