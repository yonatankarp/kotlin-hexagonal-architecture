package com.yonatankarp.shop.model.product

import com.yonatankarp.shop.model.money.Money

object ProductFixture {
    fun createTestProduct(
        price: Money,
        itemsInStock: Int = Int.MAX_VALUE,
    ) = Product(
        ProductId.randomProductId(),
        "any name",
        "any description",
        price,
        itemsInStock,
    )
}
