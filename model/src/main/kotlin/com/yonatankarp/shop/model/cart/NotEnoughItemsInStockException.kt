package com.yonatankarp.shop.model.cart

class NotEnoughItemsInStockException(message: String, val itemsInStock: Int) : Exception(message)
