package com.yonatankarp.shop.application.port.usecase.product

import com.yonatankarp.shop.model.product.Product

/**
 * The customer should be able to enter a text in a search field. The search
 * text should be at least two characters long. The search should return all
 * products where the search text appears in the title or the description
 */

interface FindProductsUseCase {
    fun findByNameOrDescription(query: String): List<Product>
}
