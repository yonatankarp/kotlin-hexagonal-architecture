plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":adapter"))
    implementation(libs.bundles.kotlin.all)
    implementation(libs.bundles.resteasy.all)

    testImplementation(testFixtures(project(":adapter")))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.tests.all)
    testImplementation(libs.restassured.core)
    testImplementation(libs.konsist.core)
}

tasks.test {
    useJUnitPlatform()
}
