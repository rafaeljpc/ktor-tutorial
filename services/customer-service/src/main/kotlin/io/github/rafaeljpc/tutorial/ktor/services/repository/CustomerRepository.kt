package io.github.rafaeljpc.tutorial.ktor.services.repository

import io.github.rafaeljpc.tutorial.ktor.services.model.Customer

class CustomerRepository {
    fun findAll(): List<Customer> {
        return listOf(
                Customer(1, "test 1", "test1@test.com"),
                Customer(2, "test 2", "test2@test.com"),
                Customer(3, "test 3", "test3@test.com")
        )
    }
}