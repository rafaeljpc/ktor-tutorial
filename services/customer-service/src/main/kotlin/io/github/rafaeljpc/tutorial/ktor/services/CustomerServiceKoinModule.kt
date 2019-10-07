package io.github.rafaeljpc.tutorial.ktor.services

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.rafaeljpc.tutorial.ktor.services.repository.CustomerRepository
import io.github.rafaeljpc.tutorial.ktor.services.service.CustomerService
import io.github.rafaeljpc.tutorial.ktor.services.service.CustomerServiceImpl
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
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

private fun Config.toProperties() = Properties().also {
    this.entrySet().forEach { e -> it.setProperty(e.key, this.getString(e.key)) }
}