package io.github.rafaeljpc.tutorial.ktor.services.service

import io.github.rafaeljpc.tutorial.ktor.services.model.Customer
import io.github.rafaeljpc.tutorial.ktor.services.repository.CustomerRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

interface CustomerService : KoinComponent {
    fun findAll(): List<Customer>
    fun create(customer: Customer)
    fun findById(id: Long): Any
}

class CustomerServiceImpl : CustomerService {

    private val repository: CustomerRepository by inject()

    override fun findAll(): List<Customer> = repository.findAll()

    override fun create(customer: Customer) = repository.create(customer)

    override fun findById(id: Long): Customer = repository.findById(id)
}
