package io.github.rafaeljpc.tutorial.ktor.services

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
import org.koin.Logger.slf4jLogger
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy
import org.koin.ktor.ext.Koin


val customerAppModule = module(createdAtStart = true) {
    singleBy<CustomerService, CustomerServiceImpl>()
    single { CustomerRepository() }
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
    install(Routing) {
        customer()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}