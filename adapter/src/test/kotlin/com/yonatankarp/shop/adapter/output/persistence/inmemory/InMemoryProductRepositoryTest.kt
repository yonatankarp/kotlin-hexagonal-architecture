package com.yonatankarp.shop.adapter.output.persistence.inmemory

import com.yonatankarp.shop.adapter.output.persistence.AbstractProductRepositoryTest

class InMemoryProductRepositoryTest :
    AbstractProductRepositoryTest<InMemoryProductRepository>() {
    override fun createProductRepository() = InMemoryProductRepository()
}
