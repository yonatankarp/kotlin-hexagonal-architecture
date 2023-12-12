package com.yonatankarp.shop.adapter.output.persistence

import com.yonatankarp.shop.model.money.Money
import com.yonatankarp.shop.model.product.Product
import com.yonatankarp.shop.model.product.ProductId
import java.util.Currency

/**
 * Demo products that are automatically stored in the product database.
 */
@Suppress("MAGIC_NUMBER")
object DemoProducts {
    private val EUR = Currency.getInstance("EUR")

    private val PLASTIC_SHEETING =
        Product(
            id = ProductId("TTKQ8NJZ"),
            name = "Plastic Sheeting",
            description = "Clear plastic sheeting, tear-resistant, tough, and durable",
            price = Money.of(EUR, 42, 99),
            itemsInStock = 55,
        )

    val COMPUTER_MONITOR =
        Product(
            id = ProductId("K3SR7PBX"),
            name = "27-Inch Curved Computer Monitor",
            description = "Enjoy big, bold and stunning panoramic views",
            price = Money.of(EUR, 159, 99),
            itemsInStock = 24081,
        )

    val MONITOR_DESK_MOUNT =
        Product(
            id = ProductId("Q3W43CNC"),
            name = "Dual Monitor Desk Mount",
            description = "Ultra wide and longer arm fits most monitors",
            price = Money.of(EUR, 119, 90),
            itemsInStock = 1079,
        )

    val LED_LIGHTS =
        Product(
            id = ProductId("WM3BPG3E"),
            name = "50ft Led Lights",
            description = "Enough lights to decorate an entire room",
            price = Money.of(EUR, 11, 69),
            itemsInStock = 3299,
        )

    val DEMO_PRODUCTS =
        listOf(
            PLASTIC_SHEETING,
            COMPUTER_MONITOR,
            MONITOR_DESK_MOUNT,
            LED_LIGHTS,
        )
}
