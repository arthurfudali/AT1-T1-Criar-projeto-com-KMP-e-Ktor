package com.dev.atv1.shared.repository

import com.dev.atv1.shared.model.CreateRaceResultRequest
import com.dev.atv1.shared.model.RaceResult
import com.dev.atv1.shared.model.UpdateRaceResultRequest

interface RaceResultRepository {
    suspend fun findAll(): List<RaceResult>
    suspend fun findById(id: Int): RaceResult?
    suspend fun create(input: CreateRaceResultRequest): RaceResult
    suspend fun update(id: Int, input: UpdateRaceResultRequest): RaceResult?
    suspend fun delete(id: Int): Boolean
}
