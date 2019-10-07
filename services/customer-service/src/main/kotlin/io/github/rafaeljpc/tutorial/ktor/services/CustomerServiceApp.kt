package io.github.rafaeljpc.tutorial.ktor.services

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.rafaeljpc.tutorial.ktor.services.repository.CustomerRepository
import io.github.rafaeljpc.tutorial.ktor.services.service.CustomerService
import io.github.rafaeljpc.tutorial.ktor.services.service.CustomerServiceImpl
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database
import org.koin.Logger.slf4jLogger
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import java.util.*
import javax.sql.DataSource


val customerAppModule = module(createdAtStart = true) {
    single<Config> {
        ConfigFactory.load()
    }
    single { CustomerServiceImpl() as CustomerService }
    single { CustomerRepository(get() as Database) }
    single<DataSource> {
        val config = get() as Config
        val profile = config.getString("profile")
        val dbConfig = HikariConfig(config.getConfig("$profile.dataSource").toProperties())
        HikariDataSource(dbConfig)
    }
    factory() {
        Database.connect(get() as DataSource)
    }
}

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson { }
    }
    install(Koin) {
        slf4jLogger()
        modules(customerAppModule)
    }

    initConfig()

    install(Routing) {
        customer()
    }
}

fun initConfig() {
    ConfigFactory.defaultApplication()
}

private fun Config.toProperties() = Properties().also {
    this.entrySet().forEach { e -> it.setProperty(e.key, this.getString(e.key)) }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}