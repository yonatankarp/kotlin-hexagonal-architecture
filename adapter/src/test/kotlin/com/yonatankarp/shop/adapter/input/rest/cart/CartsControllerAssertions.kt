package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.model.cart.Cart
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import jakarta.ws.rs.core.Response.Status.OK
import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal
import java.util.Currency

object CartsControllerAssertions {
    fun assertThatResponseIsCart(
        response: Response,
        cart: Cart,
    ) {
        assertEquals(OK.statusCode, response.statusCode)

        val json = response.jsonPath()

        cart.lineItems().forEachIndexed { index, lineItem ->
            val lineItemPrefix = "lineItems[$index]"
            assertEquals(
                json.getString("$lineItemPrefix.productId"),
                lineItem.product.id.value,
            )
            assertEquals(
                json.getString("$lineItemPrefix.productName"),
                lineItem.product.name,
            )
            assertEquals(
                json.getCurrency("$lineItemPrefix.price.currency"),
                lineItem.product.price.currency,
            )
            assertEquals(
                json.getBigDecimal("$lineItemPrefix.price.amount"),
                lineItem.product.price.amount,
            )
            assertEquals(
                json.getLong("$lineItemPrefix.quantity"),
                lineItem.quantity,
            )
        }

        assertEquals(json.getLong("numberOfItems"), cart.numberOfItems())

        assertEquals(
            json.getCurrency("subTotal.currency"),
            cart.subTotal().currency,
        )
        assertEquals(
            json.getBigDecimal("subTotal.amount"),
            cart.subTotal().amount,
        )
    }

    private fun JsonPath.getCurrency(path: String) = Currency.getInstance(getString(path))

    private fun JsonPath.getBigDecimal(path: String) = BigDecimal(getString(path))
}
