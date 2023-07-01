val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinKtor: String by project
val kmongoVersion: String by project
val commonCodecVersion: String by project


plugins {
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

group = "com.foody"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks {
    create("stage").dependsOn("installDist")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koinKtor")
// SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koinKtor")

    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")

    implementation("org.litote.kmongo:kmongo:$kmongoVersion")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongoVersion")
    implementation("commons-codec:commons-codec:$commonCodecVersion")

    //jwt
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")

}