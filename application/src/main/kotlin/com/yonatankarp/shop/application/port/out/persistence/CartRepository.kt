package com.yonatankarp.shop.application.port.out.persistence

import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId

interface CartRepository {
    fun save(cart: Cart)

    fun findByCustomerId(customerId: CustomerId): Cart?

    fun deleteByCustomerId(customerId: CustomerId)
}
