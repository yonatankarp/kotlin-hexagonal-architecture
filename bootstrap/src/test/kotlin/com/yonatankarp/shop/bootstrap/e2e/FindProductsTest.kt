package com.yonatankarp.shop.bootstrap.e2e

import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.TEST_PORT
import com.yonatankarp.shop.adapter.input.rest.product.ProductsControllerAssertions.assertThatResponseIsProductList
import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.COMPUTER_MONITOR
import com.yonatankarp.shop.adapter.output.persistence.DemoProducts.MONITOR_DESK_MOUNT
import io.restassured.RestAssured
import org.junit.jupiter.api.Test

internal class FindProductsTest : EndToEndTest() {
    @Test
    fun `given test products and a query - find products - returns matching products`() {
        // Given
        val query = "monitor"

        // When
        val response =
            RestAssured.given()
                .port(TEST_PORT)
                .queryParam("query", query)["/products"]
                .then()
                .extract()
                .response()

        // Then
        assertThatResponseIsProductList(
            response,
            listOf(COMPUTER_MONITOR, MONITOR_DESK_MOUNT),
        )
    }
}
