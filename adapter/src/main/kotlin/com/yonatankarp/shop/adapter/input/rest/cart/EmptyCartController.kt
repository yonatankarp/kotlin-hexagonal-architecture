package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.adapter.input.rest.common.CustomerIdParser.parseCustomerId
import com.yonatankarp.shop.application.port.usecase.cart.EmptyCartUseCase
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
class EmptyCartController(private val emptyCartUseCase: EmptyCartUseCase) {
    @DELETE
    @Path("/{customerId}")
    fun deleteCart(
        @PathParam("customerId") customerIdString: String,
    ) {
        val customerId = parseCustomerId(customerIdString)
        emptyCartUseCase.emptyCart(customerId)
    }
}
