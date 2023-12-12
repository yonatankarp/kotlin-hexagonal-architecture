package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.adapter.input.rest.common.CustomerIdParser.parseCustomerId
import com.yonatankarp.shop.application.port.usecase.cart.GetCartUseCase
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
class GetCartController(private val getCartUseCase: GetCartUseCase) {
    @GET
    @Path("/{customerId}")
    fun getCart(
        @PathParam("customerId") customerIdString: String,
    ): CartWebModel {
        val customerId = parseCustomerId(customerIdString)
        val cart = getCartUseCase.getCart(customerId)
        return CartWebModel.fromDomainModel(cart)
    }
}
