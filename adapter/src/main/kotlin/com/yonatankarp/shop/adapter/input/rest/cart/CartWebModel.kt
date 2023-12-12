package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.money.Money

data class CartWebModel(
    val lineItems: List<CartLineItemWebModel>,
    val numberOfItems: Int,
    val subTotal: Money?,
) {
    companion object {
        fun fromDomainModel(cart: Cart): CartWebModel =
            CartWebModel(
                lineItems = cart.lineItems().map(CartLineItemWebModel::fromDomainModel),
                numberOfItems = cart.numberOfItems().toInt(),
                subTotal = cart.subTotal(),
            )
    }
}
