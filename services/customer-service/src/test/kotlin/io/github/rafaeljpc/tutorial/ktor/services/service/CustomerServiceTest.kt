package io.github.rafaeljpc.tutorial.ktor.services.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.rafaeljpc.tutorial.ktor.services.main
import io.github.rafaeljpc.tutorial.ktor.services.model.Customer
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomerServiceTest {

    val jackson = jacksonObjectMapper()

    @Test
    fun `should create customer`() {
        withTestApplication(Application::main) {
            val log = arrayListOf<String>()

            handleRequest(method = HttpMethod.Post, uri = "/customer") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(jackson.writeValueAsString(Customer(name = "test", email = "test@test.com")))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}