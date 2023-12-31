package com.yonatankarp.shop.adapter.ext

import io.restassured.path.json.JsonPath
import java.math.BigDecimal
import java.util.Currency

object JsonExt {
    fun JsonPath.getCurrency(path: String) = Currency.getInstance(getString(path))

    fun JsonPath.getBigDecimal(path: String) = BigDecimal(getString(path)).setScale(2)
}
