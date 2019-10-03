package io.github.rafaeljpc.tutorial.ktor.services.service

import io.github.rafaeljpc.tutorial.ktor.services.model.Customer
import io.github.rafaeljpc.tutorial.ktor.services.repository.CustomerRepository

interface CustomerService {
    fun findAll(): List<Customer>
}

class CustomerServiceImpl(val repository: CustomerRepository) : CustomerService {
    override fun findAll(): List<Customer> = repository.findAll()
}
