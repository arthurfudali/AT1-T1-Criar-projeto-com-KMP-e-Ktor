package com.dev.atv1

import com.dev.atv1.config.AppEnvironment
import com.dev.atv1.db.DatabaseFactory
import com.dev.atv1.repository.ExposedDriverRepository
import com.dev.atv1.repository.ExposedRaceResultRepository
import com.dev.atv1.routes.driverRoutes
import com.dev.atv1.routes.documentationRoutes
import com.dev.atv1.routes.raceResultRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.slf4j.event.Level

fun main() {
    val environment = AppEnvironment.load()
    embeddedServer(
        Netty,
        port = environment.serverPort,
        host = "0.0.0.0",
        module = { module(environment) },
    )
        .start(wait = true)
}

fun Application.module(environment: AppEnvironment = AppEnvironment.load()) {
    install(CallLogging) {
        level = Level.INFO
    }
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to (cause.message ?: "Requisicao invalida")))
        }
        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("message" to "Erro interno no servidor", "detail" to (cause.message ?: "sem detalhes")),
            )
        }
    }

    val dataSource = DatabaseFactory.create(environment)
    DatabaseFactory.migrate(dataSource)

    val driverRepository = ExposedDriverRepository()
    val raceResultRepository = ExposedRaceResultRepository()

    routing {
        get("/") {
            call.respondText("API KMP + Ktor em execucao")
        }
        documentationRoutes()
        driverRoutes(driverRepository)
        raceResultRoutes(raceResultRepository)
    }
}
