package com.yonatankarp.shop.adapter.input.rest.common

import com.yonatankarp.shop.adapter.input.rest.common.ControllerCommons.clientErrorException
import com.yonatankarp.shop.model.product.ProductId
import jakarta.ws.rs.core.Response.Status.BAD_REQUEST

/**
 * A parser for product IDs, throwing a [jakarta.ws.rs.ClientErrorException] for
 * invalid product IDs.
 */
object ProductIdParser {
    fun parseProductId(string: String) =
        try {
            ProductId(string)
        } catch (e: IllegalArgumentException) {
            throw clientErrorException(BAD_REQUEST, "Invalid 'productId'")
        }
}
