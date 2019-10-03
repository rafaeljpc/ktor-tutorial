import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm")
}

dependencies {
    // Log
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // Ktor
    implementation("io.ktor:ktor-server-netty:1.2.5")
    implementation("io.ktor:ktor-jackson:1.2.5")

    // Koin
    implementation("org.koin:koin-ktor:2.0.1")
    implementation("org.koin:koin-logger-slf4j:2.0.1")
}

kotlin {
    experimental {
        coroutines = Coroutines.ENABLE
    }
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}