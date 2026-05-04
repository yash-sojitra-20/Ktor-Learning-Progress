package com.example

import io.ktor.server.application.*
import org.jetbrains.exposed.v1.jdbc.Database

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/ktor_tutorial_db",
        driver = "org.postgresql.Driver",
        user = "yash",
        password = "yash123"
    )
}