package com.yonatankarp.shop.bootstrap.konsist

import com.lemonappdev.konsist.api.KoModifier
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.declaration.KoPropertyDeclaration
import com.lemonappdev.konsist.api.ext.list.indexOfFirstInstance
import com.lemonappdev.konsist.api.ext.list.indexOfLastInstance
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withValueModifier
import com.lemonappdev.konsist.api.ext.list.primaryConstructors
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

@Suppress("TOO_MANY_LINES_IN_LAMBDA")
internal class GeneralRuleTest {
    @Test
    fun `files in 'ext' package must have name ending with 'Ext'`() {
        Konsist
            .scopeFromProject()
            .files
            .withPackage("..ext..")
            .assertTrue { it.hasNameEndingWith("Ext") }
    }

    @Test
    fun `properties are declared before functions`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                val lastKoPropertyDeclarationIndex =
                    it
                        .declarations(includeNested = false, includeLocal = false)
                        .indexOfLastInstance<KoPropertyDeclaration>()

                val firstKoFunctionDeclarationIndex =
                    it
                        .declarations(includeNested = false, includeLocal = false)
                        .indexOfFirstInstance<KoFunctionDeclaration>()

                if (lastKoPropertyDeclarationIndex != -1 && firstKoFunctionDeclarationIndex != -1) {
                    lastKoPropertyDeclarationIndex < firstKoFunctionDeclarationIndex
                } else {
                    true
                }
            }
    }

    @Test
    fun `companion object is last declaration in the class`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue { clazz ->
                val companionObject =
                    clazz.objects(includeNested = false).lastOrNull { obj ->
                        obj.hasModifier(KoModifier.COMPANION)
                    }

                if (companionObject != null) {
                    clazz.declarations(
                        includeNested = false,
                        includeLocal = false,
                    ).last() == companionObject
                } else {
                    true
                }
            }
    }

    @Test
    fun `every value class has parameter named 'value'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withValueModifier()
            .primaryConstructors
            .assertTrue { it.hasParameterWithName("value") }
    }

    @Test
    fun `no field should have 'm' prefix`() {
        Konsist
            .scopeFromProject()
            .classes()
            .properties()
            .assertFalse {
                val secondCharacterIsUppercase =
                    it.name.getOrNull(1)?.isUpperCase() ?: false
                it.name.startsWith('m') && secondCharacterIsUppercase
            }
    }

    @Test
    fun `no class should use Java util logging`() {
        Konsist
            .scopeFromProject()
            .files
            .assertFalse { file ->
                file.hasImport { import ->
                    import.name == "java.util.logging.."
                }
            }
    }

    @Test
    fun `package name must match file path`() {
        Konsist
            .scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }
}
