plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.kotlin.jvm)
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    spotless {
        kotlin {
            target(
                fileTree("${project.rootDir}/$name") {
                    include("**/*.kt")
                },
            )
            // see https://github.com/shyiko/ktlint#standard-rules
            trimTrailingWhitespace()
            ktlint("1.0.1")
            diktat("1.2.3").configFile("${rootProject.rootDir}/config/diktat/diktat-analysis.yml")
        }
        kotlinGradle {
            target(
                fileTree(".") {
                    include("**/*.gradle.kts")
                },
            )
            trimTrailingWhitespace()
            indentWithTabs(2)
            indentWithSpaces(4)
            endWithNewline()
            ktlint()
        }
    }

    val tasksDependencies =
        mapOf(
            "spotlessKotlin" to listOf("spotlessKotlinGradle", "compileKotlin", "compileTestKotlin", "test"),
            "spotlessKotlinGradle" to listOf("compileKotlin", "compileTestKotlin", "test"),
        )

    tasks.named("spotlessKotlin") {
        mustRunAfter("compileKotlin")
    }

    tasksDependencies.forEach { (task, dependOn) ->
        dependOn.forEach { tasks.findByName(task)!!.dependsOn(it) }
    }
}
