package com.dev.atv1.routes

import com.dev.atv1.shared.model.CreateRaceResultRequest
import com.dev.atv1.shared.model.UpdateRaceResultRequest
import com.dev.atv1.shared.repository.RaceResultRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import org.postgresql.util.PSQLException

fun Route.raceResultRoutes(raceResultRepository: RaceResultRepository) {
    route("/race-results") {
        get {
            call.respond(raceResultRepository.findAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("message" to "ID invalido"))

            val raceResult = raceResultRepository.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("message" to "Resultado nao encontrado"))

            call.respond(raceResult)
        }

        post {
            val payload = call.receive<CreateRaceResultRequest>()
            runCatching { raceResultRepository.create(payload) }
                .onSuccess { call.respond(HttpStatusCode.Created, it) }
                .onFailure {
                    if (it is PSQLException) {
                        throw BadRequestException("driverId invalido ou dados inconsistentes")
                    }
                    throw it
                }
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("message" to "ID invalido"))
            val payload = call.receive<UpdateRaceResultRequest>()

            runCatching { raceResultRepository.update(id, payload) }
                .onSuccess { updated ->
                    if (updated == null) {
                        call.respond(HttpStatusCode.NotFound, mapOf("message" to "Resultado nao encontrado"))
                    } else {
                        call.respond(updated)
                    }
                }
                .onFailure {
                    if (it is PSQLException) {
                        throw BadRequestException("driverId invalido ou dados inconsistentes")
                    }
                    throw it
                }
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("message" to "ID invalido"))

            if (!raceResultRepository.delete(id)) {
                return@delete call.respond(HttpStatusCode.NotFound, mapOf("message" to "Resultado nao encontrado"))
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
