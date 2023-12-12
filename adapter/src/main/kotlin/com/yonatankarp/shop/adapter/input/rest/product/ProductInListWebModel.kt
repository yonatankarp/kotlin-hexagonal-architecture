package com.yonatankarp.shop.adapter.input.rest.product

import com.yonatankarp.shop.model.money.Money
import com.yonatankarp.shop.model.product.Product

data class ProductInListWebModel(
    val id: String,
    val name: String,
    val price: Money,
    val itemsInStock: Int,
) {
    companion object {
        fun fromDomainModel(product: Product) =
            ProductInListWebModel(
                product.id.value,
                product.name,
                product.price,
                product.itemsInStock,
            )
    }
}
