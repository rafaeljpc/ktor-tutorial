package io.github.rafaeljpc.tutorial.ktor.services.model

data class Customer(
        var id: Long = 0,
        var name: String,
        var email: String
)