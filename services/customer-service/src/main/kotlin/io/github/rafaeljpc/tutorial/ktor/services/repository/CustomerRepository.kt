package io.github.rafaeljpc.tutorial.ktor.services.repository

import io.github.rafaeljpc.tutorial.ktor.services.model.Customer
import io.github.rafaeljpc.tutorial.ktor.services.repository.Customers.email
import io.github.rafaeljpc.tutorial.ktor.services.repository.Customers.id
import io.github.rafaeljpc.tutorial.ktor.services.repository.Customers.name
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.KoinComponent

object Customers : IdTable<Long>("customers"), KoinComponent {
    override val id: Column<EntityID<Long>> = long("id").autoIncrement("SEQ_CUSTOMER_ID").primaryKey().entityId()
    val name: Column<String> = varchar("name", 50)
    val email: Column<String> = varchar("email", 255)

    init {
        uniqueIndex("UK_CUSTOMER_NAME", name)
        uniqueIndex("UK_CUSTOMER_EMAIL", email)
    }
}

class CustomerRepository(val database: Database) : KoinComponent {

    fun findAll(): List<Customer> {
        return transaction {
            Customers.selectAll().map { row -> toModel(row) }
        }
    }

    fun create(customer: Customer) {
        transaction {
            Customers.insert {
                fillRow(it, customer)
            }
        }
    }

    fun findById(id: Long): Customer {
        return transaction {
            Customers.select { Customers.id.eq(id) }.map { row -> toModel(row) }.first()
        }
    }

    private fun fillRow(row: UpdateBuilder<Int>, customer: Customer) {
        if (customer.id > 0L) {
            row[id] = EntityID(customer.id, Customers)
        }
        row[name] = customer.name
        row[email] = customer.email
    }

    private fun toModel(row: ResultRow) = Customer(id = row[id].value, name = row[name], email = row[email])
}

