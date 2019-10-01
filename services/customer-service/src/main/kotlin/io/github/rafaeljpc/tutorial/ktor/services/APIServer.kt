package io.github.rafaeljpc.tutorial.ktor.services

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlin.random.Random

data class ToDo(var id: Long, var name: String, var description: String, var completed: Boolean)

val toDoList = arrayListOf(
        ToDo(id = 1, name = "teste 1", description = "testando", completed = false),
        ToDo(id = 2, name = "teste 2", description = "testando", completed = false),
        ToDo(id = 3, name = "teste 3", description = "testando", completed = false)
)

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson { }
    }
    routing {
        route("/todo") {
            post {
                var toDo = call.receive<ToDo>()
                toDo.id = Random.nextLong()
                toDoList.add(toDo)
                call.respond("Added")
            }
            get("/{id}") {
                call.respond(toDoList[call.parameters["id"]!!.toInt()])
            }
            get {
                call.respond(toDoList)
            }
        }
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)