package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.adapter.ext.JsonExt.getBigDecimal
import com.yonatankarp.shop.adapter.ext.JsonExt.getCurrency
import com.yonatankarp.shop.model.cart.Cart
import io.restassured.response.Response
import jakarta.ws.rs.core.Response.Status.OK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

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
                lineItem.product.id.value,
                json.getString("$lineItemPrefix.productId"),
                "Product id does not match for index $index",
            )
            assertEquals(
                lineItem.product.name,
                json.getString("$lineItemPrefix.productName"),
                "Product name does not match for index $index",
            )
            assertEquals(
                lineItem.product.price.currency,
                json.getCurrency("$lineItemPrefix.price.currency"),
                "Currency does not match for index $index",
            )
            assertEquals(
                lineItem.product.price.amount,
                json.getBigDecimal("$lineItemPrefix.price.amount"),
                "Amount does not match for index $index",
            )
            assertEquals(
                lineItem.quantity,
                json.getLong("$lineItemPrefix.quantity"),
                "Quantity does not match for index $index",
            )
        }

        assertEquals(
            cart.numberOfItems(),
            json.getLong("numberOfItems"),
            "Number of items do not match",
        )

        if (cart.subTotal() != null) {
            assertEquals(
                cart.subTotal()!!.currency.currencyCode,
                json.getString("subTotal.currency"),
            )
            assertEquals(
                cart.subTotal()!!.amount,
                json.getBigDecimal("subTotal.amount"),
            )
        } else {
            assertNull(json.getString("subTotal"))
        }
    }
}
