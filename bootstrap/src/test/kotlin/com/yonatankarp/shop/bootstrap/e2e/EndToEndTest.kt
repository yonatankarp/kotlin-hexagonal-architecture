package com.yonatankarp.shop.bootstrap.e2e

import com.yonatankarp.shop.adapter.input.rest.HttpTestCommons.TEST_PORT
import com.yonatankarp.shop.bootstrap.Launcher
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

abstract class EndToEndTest {
    companion object {
        private lateinit var server: UndertowJaxrsServer

        @BeforeAll
        @JvmStatic
        fun start() {
            server = Launcher.startOnPort(TEST_PORT)
        }

        @AfterAll
        @JvmStatic
        fun stop() {
            server.stop()
        }
    }
}
