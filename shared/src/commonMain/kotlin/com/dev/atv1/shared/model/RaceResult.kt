package com.dev.atv1.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class RaceResult(
    val id: Int,
    val season: Int,
    val grandPrix: String,
    val position: Int,
    val points: Int,
    val driverId: Int,
)

@Serializable
data class CreateRaceResultRequest(
    val season: Int,
    val grandPrix: String,
    val position: Int,
    val points: Int,
    val driverId: Int,
)

@Serializable
data class UpdateRaceResultRequest(
    val season: Int,
    val grandPrix: String,
    val position: Int,
    val points: Int,
    val driverId: Int,
)
