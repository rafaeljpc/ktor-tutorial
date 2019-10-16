import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm")
}

dependencies {
    // Log
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // Ktor
    implementation("io.ktor:ktor-server-netty:1.2.+")
    implementation("io.ktor:ktor-jackson:1.2.+")

    // database
    runtime("com.h2database:h2:1.4.+")
    implementation("com.zaxxer:HikariCP:3.4.+")
    implementation("org.jetbrains.exposed:exposed:0.17.+")

    // Koin
    implementation("org.koin:koin-ktor:2.0.1")
    implementation("org.koin:koin-logger-slf4j:2.0.1")

    // Test
    testImplementation("io.ktor:ktor-server-test-host:1.2.+")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.50")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.3.50")
}

kotlin {
    experimental {
        coroutines = Coroutines.ENABLE
    }
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}