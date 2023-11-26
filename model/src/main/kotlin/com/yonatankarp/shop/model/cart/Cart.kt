package com.yonatankarp.shop.model.cart

import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.money.Money
import com.yonatankarp.shop.model.product.Product
import com.yonatankarp.shop.model.product.ProductId

data class Cart(
    val customerId: CustomerId,
) {
    private val lineItems = mutableMapOf<ProductId, CartLineItem>()

    fun lineItems() = lineItems.values.toList()

    fun numberOfItems(): Long =
        lineItems.values
            .map(CartLineItem::quantity)
            .sum()

    fun subTotal(): Money =
        lineItems.values
            .map(CartLineItem::subTotal)
            .reduce(Money::plus)

    @Throws(NotEnoughItemsInStockException::class)
    fun addProduct(
        product: Product,
        quantity: Int,
    ) {
        lineItems
            .computeIfAbsent(product.id) { CartLineItem(product) }
            .increaseQuantityBy(quantity, product.itemsInStock)
    }
}
