package com.yonatankarp.shop.adapter.input.rest

import io.restassured.response.Response
import jakarta.ws.rs.core.Response.Status as JakartaStatus
import org.junit.jupiter.api.Assertions.assertEquals

object HttpTestCommons {
    // So the tests can run when the application runs on port 8080:
    const val TEST_PORT = 8082

    fun assertThatResponseIsError(
        response: Response,
        expectedStatus: JakartaStatus,
        expectedErrorMessage: String,
    ) {
        assertEquals(expectedStatus.statusCode, response.statusCode)

        val json = response.jsonPath()

        assertEquals(expectedStatus.statusCode, json.getInt("httpStatus"))
        assertEquals(expectedErrorMessage, json.getString("errorMessage"))
    }
}
