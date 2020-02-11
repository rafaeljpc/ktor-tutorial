package io.github.rafaeljpc.tutorial.ktor.services.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.rafaeljpc.tutorial.ktor.services.main
import io.github.rafaeljpc.tutorial.ktor.services.model.Customer
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Ignore
import kotlin.test.Test

class CustomerServiceTest {

    @Test
    @Ignore
    fun `should create customer`() =
            withTestApplication(Application::main)
            {
                val jackson = jacksonObjectMapper()

                handleRequest(HttpMethod.Post, "/customer") {
                    addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    setBody(jackson.writeValueAsString(Customer(name = "test", email = "test@test.com")))
                }
            }

}