package com.example

import io.ktor.server.application.*
import com.example.model.FakeTaskRepository
import com.example.model.PostgresTaskRepository


fun Application.module() {
//    val repository = FakeTaskRepository()
    val repository = PostgresTaskRepository()

    configureSerialization(repository)
    configureDatabases()
    configureRouting()
}