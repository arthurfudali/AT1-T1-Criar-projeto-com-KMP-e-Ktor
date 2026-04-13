package com.dev.atv1.routes

import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Route.documentationRoutes() {
    openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
    swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
}
