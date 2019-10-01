import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor-server-core:1.2.5")
    implementation("io.ktor:ktor-server-netty:1.2.5")
    implementation("io.ktor:ktor-jackson:1.2.5")
}

kotlin {
    experimental {
        coroutines = Coroutines.ENABLE
    }
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}