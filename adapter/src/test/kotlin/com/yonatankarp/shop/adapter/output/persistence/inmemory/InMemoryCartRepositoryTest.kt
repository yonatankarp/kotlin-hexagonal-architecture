package com.yonatankarp.shop.adapter.output.persistence.inmemory

import com.yonatankarp.shop.adapter.output.persistence.AbstractCartRepositoryTest
import com.yonatankarp.shop.application.port.out.persistence.CartRepository
import com.yonatankarp.shop.application.port.out.persistence.ProductRepository

internal class InMemoryCartRepositoryTest : AbstractCartRepositoryTest<CartRepository, ProductRepository>() {
    override fun createCartRepository() = InMemoryCartRepository()

    override fun createProductRepository() = InMemoryProductRepository()
}
