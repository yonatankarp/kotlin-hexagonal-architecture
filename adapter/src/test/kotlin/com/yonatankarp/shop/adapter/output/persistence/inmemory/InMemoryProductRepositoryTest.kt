package com.yonatankarp.shop.adapter.output.persistence.inmemory

import com.yonatankarp.shop.adapter.output.persistence.AbstractProductRepositoryTest

internal class InMemoryProductRepositoryTest :
    AbstractProductRepositoryTest<InMemoryProductRepository>() {
    override fun createProductRepository() = InMemoryProductRepository()
}
