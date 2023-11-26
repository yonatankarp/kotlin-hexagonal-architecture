package com.yonatankarp.shop.application.service.product

import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.application.port.usecase.product.FindProductsUseCase
import com.yonatankarp.shop.model.product.Product

class FindProductsService(
    private val productRepository: ProductRepository,
) : FindProductsUseCase {
    override fun findByNameOrDescription(query: String): List<Product> {
        require(query.length >= 2) { "'query' must be at least two characters long" }

        return productRepository.findByNameOrDescription(query)
    }
}
