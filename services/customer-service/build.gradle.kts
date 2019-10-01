plugins {
    application
    kotlin("jvm")
}

dependencies {
    implementation("io.ktor:ktor-server-core:0.9.5")
    implementation("io.ktor:ktor-server-netty:0.9.5")
    implementation("io.ktor:ktor-jackson:0.9.5")
}
