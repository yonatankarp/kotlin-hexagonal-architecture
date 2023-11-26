package com.yonatankarp.shop.model.product

import com.yonatankarp.shop.model.money.Money

data class Product(
    val id: ProductId,
    var name: String,
    var description: String,
    var price: Money,
    var itemsInStock: Int,
)
