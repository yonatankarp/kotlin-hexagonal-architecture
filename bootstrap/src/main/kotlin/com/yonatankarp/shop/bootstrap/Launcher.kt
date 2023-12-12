package com.yonatankarp.shop.bootstrap

import com.yonatankarp.shop.bootstrap.Launcher.PORT
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer

/**
 * Launcher for the application: starts the Undertow server and deploys the shop application.
 */
object Launcher {
    const val PORT = 8080

    fun startOnPort(port: Int): UndertowJaxrsServer =
        UndertowJaxrsServer().setPort(port)
            .apply { startServer() }

    private fun UndertowJaxrsServer.startServer() {
        start()
        deploy(RestEasyUndertowShopApplication::class.java)
    }
}

fun main() {
    Launcher.startOnPort(PORT)
}
