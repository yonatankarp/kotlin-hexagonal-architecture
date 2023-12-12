package com.yonatankarp.shop.adapter.input.rest.product

import com.yonatankarp.shop.adapter.utilities.JsonExtensions.getBigDecimal
import com.yonatankarp.shop.adapter.utilities.JsonExtensions.getCurrency
import com.yonatankarp.shop.model.product.Product
import io.restassured.path.json.JsonPath
import io.restassured.response.Response
import jakarta.ws.rs.core.Response.Status.OK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

object ProductsControllerAssertions {
    fun assertThatResponseIsProductList(
        response: Response,
        products: List<Product>,
    ) {
        assertEquals(OK.statusCode, response.statusCode())

        val json = response.jsonPath()

        for (i in products.indices) {
            assertThatJsonProductMatchesProduct(
                json = json,
                jsonHasDescription = false,
                prefix = "[$i].",
                product = products[i],
            )
        }
    }

    private fun assertThatJsonProductMatchesProduct(
        json: JsonPath,
        jsonHasDescription: Boolean,
        prefix: String,
        product: Product,
    ) {
        assertEquals(product.id.value, json.getString("${prefix}id"))
        assertEquals(product.name, json.getString("${prefix}name"))

        if (jsonHasDescription) {
            assertEquals(
                product.description,
                json.getString("${prefix}description"),
            )
        } else {
            assertNull(json.getString("${prefix}description"))
        }

        assertEquals(
            product.price.currency,
            json.getCurrency("${prefix}price.currency"),
        )
        assertEquals(
            product.price.amount,
            json.getBigDecimal("${prefix}price.amount"),
        )

        assertEquals(product.itemsInStock, json.getInt("${prefix}itemsInStock"))
    }
}
