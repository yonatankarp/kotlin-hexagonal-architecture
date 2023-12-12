package com.yonatankarp.shop.adapter.input.rest.common

import jakarta.ws.rs.ClientErrorException
import jakarta.ws.rs.core.Response

object ControllerCommons {
    fun clientErrorException(
        status: Response.Status,
        message: String,
    ) = ClientErrorException(errorResponse(status, message))

    fun errorResponse(
        status: Response.Status,
        message: String,
    ) = Response
        .status(status)
        .entity(ErrorEntity(status.statusCode, message))
        .build()
}
