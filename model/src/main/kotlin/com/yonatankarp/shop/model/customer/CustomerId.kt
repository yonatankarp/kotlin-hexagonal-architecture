package com.yonatankarp.shop.model.customer

@JvmInline
value class CustomerId(val value: Int) {
    init {
        require(value > 0) { "'value' must be a positive integer" }
    }
}
