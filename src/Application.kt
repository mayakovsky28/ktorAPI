package com.ktor1

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

class Course(var courseId: Int, var title: String, var complexity: String, var isActive: Boolean)

val course1 = Course(1, "Learn how to read", "tricky", true)
val course2 = Course(2, "Learn how to write", "easy", true)
val course3 = Course(3, "Kotlin for dummies", "medium", true)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    //use extension
    course1.setInactive()
    course3.setInactive()

    val client = HttpClient(Apache) {
    }
    install(ContentNegotiation) {
        jackson {
        }
    }

    routing {
        get("/") {
            call.respondText("Welcome", contentType = ContentType.Text.Plain)
        }

        get("/course/{courseNumber}") {
            when (call.parameters["courseNumber"]) {
                "1" -> call.respond(course1)
                "2" -> call.respond(course2)
                "top", "3" -> call.respond(course3)
                else -> call.respondText("Error")
            }
        }
    }
}