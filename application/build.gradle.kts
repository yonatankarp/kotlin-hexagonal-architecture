plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":model"))
    implementation(libs.bundles.kotlin.all)

    testImplementation(testFixtures(project(":model")))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.tests.all)
}

tasks.test {
    useJUnitPlatform()
}
