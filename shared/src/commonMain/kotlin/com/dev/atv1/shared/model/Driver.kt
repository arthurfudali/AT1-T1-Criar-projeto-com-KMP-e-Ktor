package com.dev.atv1.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val id: Int,
    val name: String,
    val team: String,
    val nationality: String,
)

@Serializable
data class CreateDriverRequest(
    val name: String,
    val team: String,
    val nationality: String,
)

@Serializable
data class UpdateDriverRequest(
    val name: String,
    val team: String,
    val nationality: String,
)
