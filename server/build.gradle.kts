plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktor)
    application
}

group = "com.dev.atv1"
version = "1.0.0"
application {
    mainClass.set("com.dev.atv1.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.serverContentNegotiation)
    implementation(libs.ktor.serializationKotlinxJson)
    implementation(libs.ktor.serverCallLogging)
    implementation(libs.ktor.serverStatusPages)
    implementation(libs.ktor.serverSwagger)
    implementation(libs.ktor.serverOpenApi)
    implementation(libs.hikari)
    implementation(libs.postgresql)
    implementation(libs.flyway)
    implementation(libs.flyway.postgresql)
    implementation(libs.dotenv.kotlin)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}
