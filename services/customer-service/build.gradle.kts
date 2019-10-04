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

    // database
    runtime("com.h2database:h2:1.4.+")
    implementation("com.zaxxer:HikariCP:3.4.+")
    implementation("org.jetbrains.exposed:exposed:0.17.+")
    implementation("org.flywaydb:flyway-core:6.0.+")

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