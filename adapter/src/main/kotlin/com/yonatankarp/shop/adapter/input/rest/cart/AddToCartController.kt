package com.yonatankarp.shop.adapter.input.rest.cart

import com.yonatankarp.shop.adapter.input.rest.common.ControllerCommons.clientErrorException
import com.yonatankarp.shop.adapter.input.rest.common.CustomerIdParser.parseCustomerId
import com.yonatankarp.shop.adapter.input.rest.common.ProductIdParser.parseProductId
import com.yonatankarp.shop.application.port.usecase.cart.AddToCartUseCase
import com.yonatankarp.shop.application.port.usecase.cart.ProductNotFoundException
import com.yonatankarp.shop.model.cart.NotEnoughItemsInStockException
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response.Status.BAD_REQUEST

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
class AddToCartController(private val addToCartUseCase: AddToCartUseCase) {
    @POST
    @Path("/{customerId}/line-items")
    fun addLineItem(
        @PathParam("customerId") customerIdString: String,
        @QueryParam("productId") productIdString: String,
        @QueryParam("quantity") quantity: Int,
    ): CartWebModel {
        val customerId = parseCustomerId(customerIdString)
        val productId = parseProductId(productIdString)

        return try {
            val cart =
                addToCartUseCase(customerId, productId, quantity)
            CartWebModel.fromDomainModel(cart)
        } catch (e: ProductNotFoundException) {
            throw clientErrorException(
                BAD_REQUEST,
                "The requested product does not exist",
            )
        } catch (e: NotEnoughItemsInStockException) {
            throw clientErrorException(
                BAD_REQUEST,
                "Only ${e.itemsInStock} items in stock",
            )
        }
    }
}
