package com.yonatankarp.shop.bootstrap.e2e

import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.TEST_PORT
import com.yonatankarp.shop.adapter.input.rest.cart.CartsControllerAssertions.assertThatResponseIsCart
import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.LED_LIGHTS
import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.MONITOR_DESK_MOUNT
import com.yonatankarp.shop.model.cart.Cart
import com.yonatankarp.shop.model.customer.CustomerId
import io.restassured.RestAssured
import jakarta.ws.rs.core.Response
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class CartTest : EndToEndTest() {
    @Test
    @Order(1)
    fun `given an empty cart - add line item - adds the line item and returns the cart with the added item`() {
        // When
        val response =
            RestAssured
                .given()
                .port(TEST_PORT)
                .queryParam("productId", LED_LIGHTS.id.value)
                .queryParam("quantity", 3)
                .post("$CARTS_PATH/line-items")
                .then()
                .extract()
                .response()

        // Then
        val expectedCart = Cart(TEST_CUSTOMER_ID)
        expectedCart.addProduct(LED_LIGHTS, 3)

        assertThatResponseIsCart(response, expectedCart)
    }

    @Test
    @Order(2)
    fun `given a cart with one line item - add line item - adds the line item and returns a cart with two line items`() {
        // When
        val response =
            RestAssured
                .given()
                .port(TEST_PORT)
                .queryParam("productId", MONITOR_DESK_MOUNT.id.value)
                .queryParam("quantity", 1)
                .post("$CARTS_PATH/line-items")
                .then()
                .extract()
                .response()

        // Then
        val expectedCart = Cart(TEST_CUSTOMER_ID)
        expectedCart.addProduct(LED_LIGHTS, 3)
        expectedCart.addProduct(MONITOR_DESK_MOUNT, 1)

        assertThatResponseIsCart(response, expectedCart)
    }

    @Test
    @Order(3)
    fun `given a cart with two line items - get cart - returns the cart`() {
        // When
        val response =
            RestAssured
                .given()
                .port(TEST_PORT)
                .get(CARTS_PATH)
                .then()
                .extract()
                .response()

        // Then
        val expectedCart = Cart(TEST_CUSTOMER_ID)
        expectedCart.addProduct(LED_LIGHTS, 3)
        expectedCart.addProduct(MONITOR_DESK_MOUNT, 1)

        assertThatResponseIsCart(response, expectedCart)
    }

    @Test
    @Order(4)
    fun `given a cart with two line items - delete - returns status code no content`() {
        RestAssured
            .given()
            .port(TEST_PORT)
            .delete(CARTS_PATH)
            .then()
            .statusCode(Response.Status.NO_CONTENT.statusCode)
    }

    @Test
    @Order(5)
    fun `given an emptied cart - get cart - returns an empty cart`() {
        // When
        val response =
            RestAssured
                .given()
                .port(TEST_PORT)
                .get(CARTS_PATH)
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsCart(response, Cart(TEST_CUSTOMER_ID))
    }

    companion object {
        private val TEST_CUSTOMER_ID = CustomerId(61157)
        private val CARTS_PATH = "/carts/${TEST_CUSTOMER_ID.value}"
    }
}
