package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.model.cart.CartLineItem
import com.yonatankarp.shop.model.money.Money
import com.yonatankarp.shop.model.product.Product

data class CartLineItemWebModel(
    val productId: String,
    val productName: String,
    val price: Money,
    val quantity: Long,
) {
    companion object {
        fun fromDomainModel(lineItem: CartLineItem): CartLineItemWebModel {
            val product: Product = lineItem.product
            return CartLineItemWebModel(
                product.id.value,
                product.name,
                product.price,
                lineItem.quantity,
            )
        }
    }
}
