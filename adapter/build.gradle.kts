plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-test-fixtures`
}

dependencies {
    api(project(":model"))
    api(project(":application"))
    implementation(libs.bundles.kotlin.all)
    implementation(libs.jakarta.api)

    testImplementation(testFixtures(project(":model")))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.tests.all)
    testImplementation(libs.bundles.resteasy.all)

    testFixturesImplementation(platform(libs.junit.bom))
    testFixturesImplementation(libs.junit)
    testFixturesImplementation(libs.restassured.core)
}

tasks.test {
    useJUnitPlatform()
}
