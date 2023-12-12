package com.yonatankarp.shop.bootstrap

import com.yonatankarp.shop.adapter.input.rest.cart.AddToCartController
import com.yonatankarp.shop.adapter.input.rest.cart.EmptyCartController
import com.yonatankarp.shop.adapter.input.rest.cart.GetCartController
import com.yonatankarp.shop.adapter.input.rest.product.FindProductsController
import com.yonatankarp.shop.adapter.output.persistence.inmemory.InMemoryCartRepository
import com.yonatankarp.shop.adapter.output.persistence.inmemory.InMemoryProductRepository
import com.yonatankarp.shop.application.service.cart.AddToCartService
import com.yonatankarp.shop.application.service.cart.EmptyCartService
import com.yonatankarp.shop.application.service.cart.GetCartService
import com.yonatankarp.shop.application.service.product.FindProductsService
import jakarta.ws.rs.core.Application

/**
 * The application configuration for the Undertow server. Instantiates the
 * adapters and use cases, and wires them.
 */
class RestEasyUndertowShopApplication : Application() {
    private lateinit var cartRepository: InMemoryCartRepository
    private lateinit var productRepository: InMemoryProductRepository

    override fun getSingletons(): Set<Any> {
        initPersistenceAdapters()

        return setOf(
            addToCartController(),
            getCartController(),
            emptyCartController(),
            findProductsController(),
        )
    }

    private fun initPersistenceAdapters() {
        cartRepository = InMemoryCartRepository()
        productRepository = InMemoryProductRepository()
    }

    private fun addToCartController(): AddToCartController {
        val addToCartUseCase =
            AddToCartService(cartRepository, productRepository)
        return AddToCartController(addToCartUseCase)
    }

    private fun getCartController(): GetCartController {
        val getCartUseCase = GetCartService(cartRepository)
        return GetCartController(getCartUseCase)
    }

    private fun emptyCartController(): EmptyCartController {
        val emptyCartUseCase = EmptyCartService(cartRepository)
        return EmptyCartController(emptyCartUseCase)
    }

    private fun findProductsController(): FindProductsController {
        val findProductsUseCase = FindProductsService(productRepository)
        return FindProductsController(findProductsUseCase)
    }
}
