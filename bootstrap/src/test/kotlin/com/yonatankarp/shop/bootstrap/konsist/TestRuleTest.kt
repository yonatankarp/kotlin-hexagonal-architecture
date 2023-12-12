package com.yonatankarp.shop.bootstrap.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

@Suppress("TOO_MANY_LINES_IN_LAMBDA")
internal class TestRuleTest {
    @Test
    fun `test classes should be internal`() {
        Konsist
            .scopeFromTest()
            .classes()
            .assertTrue { it.hasInternalModifier }
    }

    @Test
    fun `classes with 'Test' Annotation should have 'Test' suffix`() {
        Konsist
            .scopeFromSourceSet("test")
            .classes()
            .filter { clazz ->
                clazz.functions()
                    .any { func -> func.hasAnnotationOf(Test::class) }
            }
            .assertTrue { it.hasNameEndingWith("Test") }
    }

    @Test
    fun `all konsist tests have 'RuleTest' suffix`() {
        Konsist
            .scopeFromSourceSet("test")
            .classes()
            .withPackage("..konsist..")
            .assertTrue { it.hasNameEndingWith("RuleTest") }
    }
}
