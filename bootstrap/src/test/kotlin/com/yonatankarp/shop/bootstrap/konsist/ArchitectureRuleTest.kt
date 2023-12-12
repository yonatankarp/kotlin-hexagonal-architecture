package com.yonatankarp.shop.bootstrap.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

@Suppress("TOO_MANY_LINES_IN_LAMBDA")
internal class ArchitectureRuleTest {
    @Test
    fun `clean architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                val model =
                    Layer(
                        name = "model",
                        definedBy = "com.yonatankarp.shop.model..",
                    )
                val application =
                    Layer(
                        name = "application",
                        definedBy = "com.yonatankarp.shop.application..",
                    )
                val port =
                    Layer(
                        name = "port",
                        definedBy = "com.yonatankarp.shop.application.port..",
                    )
                val service =
                    Layer(
                        name = "service",
                        definedBy = "com.yonatankarp.shop.application.service..",
                    )
                val adapter =
                    Layer(
                        name = "adapter",
                        definedBy = "com.yonatankarp.shop.adapter..",
                    )

                model.dependsOnNothing()

                application.dependsOn(model, port)

                port.dependsOn(model, application)

                service.dependsOn(model, application, port)

                adapter.dependsOn(model, application, port)
            }
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain' and 'usecase' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..port..usecase..") }
    }

    @Test
    fun `classes with 'UseCase' suffix should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { clazz ->
                val hasSingleInvokeOperatorMethod =
                    clazz.hasFunction { function ->
                        function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                    }

                hasSingleInvokeOperatorMethod && clazz.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1
            }
    }

    @Test
    fun `interfaces with 'Repository' suffix should reside in 'persistence' package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("Repository")
            .assertTrue { it.resideInPackage("..persistence..") }
    }

    @Test
    fun `interfaces with 'UseCase' suffix should reside in 'port' input package`() {
        Konsist
            .scopeFromProject()
            .interfaces()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..port..usecase..") }
    }

    @Test
    fun `every UseCase class has test`() {
        Konsist
            .scopeFromProduction()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.hasTestClass() }
    }
}
