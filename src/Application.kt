package com.ktor1

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson

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
        get("/top") {
            call.respond(course3)
        }

        get("/course/{courseNumber}") {
            val courseNumber = call.parameters["courseNumber"]
            when (courseNumber) {
                "1" -> call.respond(course1)
                "2" -> call.respond(course2)
                "3" -> call.respond(course3)
                else -> call.respondText("Error")
            }
        }
    }
}