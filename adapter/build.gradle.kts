plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":model"))
    implementation(project(":application"))
    implementation(libs.bundles.kotlin.all)
    implementation(libs.jakarta.api)

    testImplementation(testFixtures(project(":model")))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.tests.all)
    testImplementation(libs.restassured.core)
    testImplementation(libs.bundles.resteasy.all)
}

tasks.test {
    useJUnitPlatform()
}
