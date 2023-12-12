package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.TEST_PORT
import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.assertThatResponseIsError
import com.yonatankarp.shop.adapter.input.rest.cart.CartsControllerAssertions.assertThatResponseIsCart
import com.yonatankarp.shop.application.port.usecase.cart.AddToCartUseCase
import com.yonatankarp.shop.application.port.usecase.cart.EmptyCartUseCase
import com.yonatankarp.shop.application.port.usecase.cart.GetCartUseCase
import com.yonatankarp.shop.application.port.usecase.cart.ProductNotFoundException
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.cart.NotEnoughItemsInStockException
import com.yonatankarp.shop.model.customer.CustomerId
import com.yonatankarp.shop.model.money.MoneyFixture.euros
import com.yonatankarp.shop.model.product.ProductFixture.createTestProduct
import com.yonatankarp.shop.model.product.ProductId
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.restassured.RestAssured.given
import jakarta.ws.rs.core.Application
import jakarta.ws.rs.core.Response.Status.BAD_REQUEST
import jakarta.ws.rs.core.Response.Status.NO_CONTENT
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CartsControllerTest {
    @BeforeEach
    fun resetMocks() {
        clearAllMocks()
    }

    @Test
    fun `given a syntactically invalid customerId - get cart - returns an error`() {
        // Given
        val customerId = "foo"

        // When
        val response =
            given()
                .port(TEST_PORT)
                .get("/carts/$customerId")
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsError(
            response,
            BAD_REQUEST,
            "Invalid 'customerId'",
        )
    }

    @Test
    fun `given a valid customer id and a cart - get cart - requests cart from use case and returns it`() {
        // Given
        val customerId = TEST_CUSTOMER_ID
        val cart = Cart(customerId)
        cart.addProduct(TEST_PRODUCT_1, 3)
        cart.addProduct(TEST_PRODUCT_2, 5)

        every { getCartUseCase.getCart(customerId) } returns cart

        // When
        val response =
            given().port(TEST_PORT)
                .get("/carts/${customerId.value}")
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsCart(response, cart)
    }

    @Test
    fun `given some test data - add line item - invokes add to cart use case and returns updated cart`() {
        // Given
        val customerId = TEST_CUSTOMER_ID
        val productId: ProductId = TEST_PRODUCT_1.id
        val quantity = 5
        val cart = Cart(customerId)
        cart.addProduct(TEST_PRODUCT_1, quantity)

        every {
            addToCartUseCase.addToCart(
                customerId,
                productId,
                quantity,
            )
        } returns cart

        // When
        val response =
            given()
                .port(TEST_PORT)
                .queryParam("productId", productId.value)
                .queryParam("quantity", quantity)
                .post("/carts/${customerId.value}/line-items")
                .then()
                .extract()
                .response()

        // When
        assertThatResponseIsCart(response, cart)
    }

    @Test
    fun `given an invalid product id - add line item - returns an error`() {
        // Given
        val customerId = TEST_CUSTOMER_ID
        val productId = ""
        val quantity = 5

        // When
        val response =
            given()
                .port(TEST_PORT)
                .queryParam("productId", productId)
                .queryParam("quantity", quantity)
                .post("/carts/${customerId.value}/line-items")
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsError(
            response,
            BAD_REQUEST,
            "Invalid 'productId'",
        )
    }

    @Test
    fun `given product not found - add line item - returns an error`() {
        // Given
        val customerId = TEST_CUSTOMER_ID
        val productId = ProductId.randomProductId()
        val quantity = 5
        every {
            addToCartUseCase.addToCart(
                customerId,
                productId,
                quantity,
            )
        } throws ProductNotFoundException()

        // When
        val response =
            given()
                .port(TEST_PORT)
                .queryParam("productId", productId.value)
                .queryParam("quantity", quantity)
                .post(("/carts/${customerId.value}/line-items"))
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsError(
            response,
            BAD_REQUEST,
            "The requested product does not exist",
        )
    }

    @Test
    fun `given not enough items in stock - add line item - returns an error`() {
        // Given
        val customerId = TEST_CUSTOMER_ID
        val productId = ProductId.randomProductId()
        val quantity = 5
        every {
            addToCartUseCase.addToCart(
                customerId,
                productId,
                quantity,
            )
        } throws
            NotEnoughItemsInStockException(
                message = "Not enough items in stock",
                itemsInStock = 2,
            )

        // When
        val response =
            given()
                .port(TEST_PORT)
                .queryParam("productId", productId.value)
                .queryParam("quantity", quantity)
                .post(("/carts/${customerId.value}/line-items"))
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsError(
            response,
            BAD_REQUEST,
            "Only 2 items in stock",
        )
    }

    @Test
    fun `given a customer id - delete cart - invokes delete cart use case and returns updated cart`() {
        // Given
        val customerId = TEST_CUSTOMER_ID

        // When
        given()
            .port(TEST_PORT)
            .delete("/carts/${customerId.value}")
            .then()
            .statusCode(NO_CONTENT.statusCode)

        // Then
        verify { emptyCartUseCase.emptyCart(customerId) }
    }

    companion object {
        private val TEST_CUSTOMER_ID = CustomerId(61157)
        private val TEST_PRODUCT_1 = createTestProduct(euros(19, 99))
        private val TEST_PRODUCT_2 = createTestProduct(euros(25, 99))

        private val addToCartUseCase = mockk<AddToCartUseCase>(relaxed = true)
        private val getCartUseCase = mockk<GetCartUseCase>(relaxed = true)
        private val emptyCartUseCase = mockk<EmptyCartUseCase>(relaxed = true)

        private var server =
            UndertowJaxrsServer()
                .setPort(TEST_PORT)
                .start()
                .deploy(
                    object : Application() {
                        override fun getSingletons() =
                            setOf(
                                AddToCartController(addToCartUseCase),
                                GetCartController(getCartUseCase),
                                EmptyCartController(emptyCartUseCase),
                            )
                    },
                )

        @JvmStatic
        @AfterAll
        fun stop() {
            server.stop()
        }
    }
}
