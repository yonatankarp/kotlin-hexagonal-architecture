package com.yonatankarp.shop.adapter.input.rest.product

import com.yonatankarp.shop.adapter.input.rest.common.ControllerCommons.clientErrorException
import com.yonatankarp.shop.application.port.usecase.product.FindProductsUseCase
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response.Status.BAD_REQUEST

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
class FindProductsController(private val findProductsUseCase: FindProductsUseCase) {
    @GET
    fun findProducts(
        @QueryParam("query") query: String,
    ): List<ProductInListWebModel> {
        val products =
            try {
                findProductsUseCase.findByNameOrDescription(query)
            } catch (e: IllegalArgumentException) {
                throw clientErrorException(BAD_REQUEST, "Invalid 'query'")
            }

        return products.map(ProductInListWebModel::fromDomainModel)
    }
}
