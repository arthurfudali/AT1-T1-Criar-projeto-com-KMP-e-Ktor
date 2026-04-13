package com.dev.atv1.routes

import com.dev.atv1.shared.model.CreateDriverRequest
import com.dev.atv1.shared.model.UpdateDriverRequest
import com.dev.atv1.shared.repository.DriverRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.driverRoutes(driverRepository: DriverRepository) {
    route("/drivers") {
        get {
            call.respond(driverRepository.findAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("message" to "ID invalido"))

            val driver = driverRepository.findById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("message" to "Piloto nao encontrado"))

            call.respond(driver)
        }

        post {
            val payload = call.receive<CreateDriverRequest>()
            val created = driverRepository.create(payload)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, mapOf("message" to "ID invalido"))
            val payload = call.receive<UpdateDriverRequest>()

            val updated = driverRepository.update(id, payload)
                ?: return@put call.respond(HttpStatusCode.NotFound, mapOf("message" to "Piloto nao encontrado"))

            call.respond(updated)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("message" to "ID invalido"))

            if (!driverRepository.delete(id)) {
                return@delete call.respond(HttpStatusCode.NotFound, mapOf("message" to "Piloto nao encontrado"))
            }
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
