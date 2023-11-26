package com.yonatankarp.shop.model.product

import kotlin.random.Random

@JvmInline
value class ProductId(val value: String) {
    init {
        require(value.isNotBlank()) { "'value' must not be empty" }
    }

    companion object {
        private const val ALPHABET = "1234567890ABCDEFGHIJKLMNPQRSTUVWXYZ"
        private const val LENGTH_OF_NEW_PRODUCT_IDS = 8

        fun randomProductId(): ProductId =
            (1..LENGTH_OF_NEW_PRODUCT_IDS)
                .map { Random.nextInt(0, ALPHABET.length) }
                .map(ALPHABET::get)
                .joinToString("")
                .let { ProductId(it) }
    }
}
