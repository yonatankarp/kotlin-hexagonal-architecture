package com.yonatankarp.shop.model.cart

import com.yonatankarp.shop.model.money.Money
import com.yonatankarp.shop.model.product.Product

data class CartLineItem(
    val product: Product,
    var quantity: Long = 0,
) {
    val subTotal: Money get() = product.price * quantity

    fun increaseQuantityBy(
        augend: Int,
        itemsInStock: Int,
    ) {
        require(augend > 0) { "You must add at least one item" }

        val newQuantity = quantity + augend
        if (itemsInStock < newQuantity) {
            throw NotEnoughItemsInStockException(
                "Product ${product.id} has less items in stock (${product.itemsInStock}) than the requested total quantity ($newQuantity)",
                product.itemsInStock,
            )
        }

        quantity = newQuantity
    }
}
