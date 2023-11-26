package com.yonatankarp.shop.application.port.out.persistence

import com.yonatankarp.shop.model.product.Product
import com.yonatankarp.shop.model.product.ProductId

interface ProductRepository {
    fun save(product: Product)

    fun findById(productId: ProductId?): Product?

    fun findByNameOrDescription(query: String): List<Product>
}
