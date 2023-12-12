package com.yonatankarp.shop.adapter.output.persistence.inmemory

import com.yonatankarp.shop.adapter.output.persistence.DemoProducts
import com.yonatankarp.shop.application.port.out.persistence.ProductRepository
import com.yonatankarp.shop.model.product.Product
import com.yonatankarp.shop.model.product.ProductId
import java.util.concurrent.ConcurrentHashMap

class InMemoryProductRepository(
    private val products: MutableMap<ProductId, Product> = ConcurrentHashMap(),
) : ProductRepository {
    init {
        createDemoProducts()
    }

    private fun createDemoProducts() {
        DemoProducts.DEMO_PRODUCTS.forEach(this::save)
    }

    override fun save(product: Product) {
        products[product.id] = product
    }

    override fun findById(productId: ProductId): Product? = products.getOrElse(productId) { null }

    override fun findByNameOrDescription(query: String) =
        products.values
            .filter { matchesQuery(it, query.lowercase()) }
            .toList()

    private fun matchesQuery(
        product: Product,
        query: String,
    ) = query in product.name.lowercase() || query in product.description.lowercase()
}
