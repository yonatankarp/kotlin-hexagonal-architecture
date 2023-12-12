package com.yonatankarp.shop.adapter.input.rest.product

import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.TEST_PORT
import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.assertThatResponseIsError
import com.yonatankarp.shop.adapter.input.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList
import com.yonatankarp.shop.application.port.usecase.product.FindProductsUseCase
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.restassured.RestAssured
import jakarta.ws.rs.core.Application
import jakarta.ws.rs.core.Response
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProductsControllerTest {
    @BeforeEach
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `given a query and a list of products - find products - requests products via query and returns them`() {
        // Given
        val query = "foo"
        val productList = listOf(TEST_PRODUCT_1, TEST_PRODUCT_2)
        every { findProductsUseCase.findByNameOrDescription(query) } returns productList

        // When
        val response =
            RestAssured.given()
                .port(TEST_PORT)
                .queryParam("query", query)["/products"]
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsProductList(response, productList)
    }

    @Test
    fun `given a too short query - find products - returns error`() {
        val query = "e"
        every { findProductsUseCase.findByNameOrDescription(query) } throws IllegalArgumentException()

        val response =
            RestAssured.given()
                .port(TEST_PORT)
                .queryParam("query", query)["/products"]
                .then()
                .extract()
                .response()

        assertThatResponseIsError(
            response,
            Response.Status.BAD_REQUEST,
            "Invalid 'query'",
        )
    }

    companion object {
        private val TEST_PRODUCT_1 = createTestProduct(euros(19, 99))
        private val TEST_PRODUCT_2 = createTestProduct(euros(25, 99))

        private val findProductsUseCase = mockk<FindProductsUseCase>()

        private val server =
            UndertowJaxrsServer()
                .setPort(TEST_PORT)
                .start()
                .deploy(
                    object : Application() {
                        override fun getSingletons(): Set<Any> =
                            java.util.Set.of<Any>(
                                FindProductsController(
                                    findProductsUseCase,
                                ),
                            )
                    },
                )

        @AfterAll
        @JvmStatic
        fun stop() {
            server!!.stop()
        }
    }
}
