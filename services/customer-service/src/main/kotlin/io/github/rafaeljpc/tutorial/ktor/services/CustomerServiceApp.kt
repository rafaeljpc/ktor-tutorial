package io.github.rafaeljpc.tutorial.ktor.services

import com.typesafe.config.ConfigFactory
import io.github.rafaeljpc.tutorial.ktor.services.repository.Customers
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
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transactionManager
import org.koin.Logger.slf4jLogger
import org.koin.ktor.ext.Koin


fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson { }
    }
    install(Koin) {
        slf4jLogger()
        modules(customerAppModule)

        val database = koin.get() as Database
        database.transactionManager.newTransaction()
        SchemaUtils.createMissingTablesAndColumns(Customers)
    }

    initConfig()

    install(Routing) {
        customer()
    }
}

fun initConfig() {
    ConfigFactory.defaultApplication()
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}