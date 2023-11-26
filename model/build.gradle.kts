plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-test-fixtures`
}

dependencies {
    implementation(libs.bundles.kotlin.all)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.tests.all)
}

tasks.test {
    useJUnitPlatform()
}
