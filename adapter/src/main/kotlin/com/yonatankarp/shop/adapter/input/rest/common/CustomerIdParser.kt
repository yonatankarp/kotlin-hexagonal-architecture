package com.yonatankarp.shop.adapter.input.rest.common

import com.yonatankarp.shop.adapter.input.rest.common.ControllerCommons.clientErrorException
import com.yonatankarp.shop.model.customer.CustomerId
import jakarta.ws.rs.core.Response.Status.BAD_REQUEST

/**
 * A parser for customer IDs, throwing a [jakarta.ws.rs.ClientErrorException]
 * for invalid customer IDs.
 */
object CustomerIdParser {
    fun parseCustomerId(string: String) =
        try {
            CustomerId(string.toInt())
        } catch (e: IllegalArgumentException) {
            throw clientErrorException(BAD_REQUEST, "Invalid 'customerId'")
        }
}
