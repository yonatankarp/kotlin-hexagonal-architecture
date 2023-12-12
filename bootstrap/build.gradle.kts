plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.bundles.kotlin.all)

    implementation("com.lemonappdev:konsist:0.13.0")

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.tests.all)
}

tasks.test {
    useJUnitPlatform()
}
