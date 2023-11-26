package com.yonatankarp.shop.model.cart

import com.yonatankarp.shop.model.customer.CustomerId
import java.util.concurrent.ThreadLocalRandom

object CartFixture {
    fun emptyCartForRandomCustomer() = Cart(CustomerId(ThreadLocalRandom.current().nextInt(1_000_000)))
}
