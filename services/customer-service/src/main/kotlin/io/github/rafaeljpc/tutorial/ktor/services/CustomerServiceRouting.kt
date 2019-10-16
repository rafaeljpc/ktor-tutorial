package io.github.rafaeljpc.tutorial.ktor.services

import io.github.rafaeljpc.tutorial.ktor.services.model.Customer
import io.github.rafaeljpc.tutorial.ktor.services.service.CustomerService
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Route.customer() {

    val customerService by inject<CustomerService>()

    route("/customer") {
        get {
            call.respond(customerService.findAll())
        }
        get("/{id}") {
            call.respond(customerService.findById(call.parameters["id"]!!.toLong()))
        }
        post {
            val customer = call.receive<Customer>()
            customerService.create(customer)
            call.respond(HttpStatusCode.OK)
        }
    }
}