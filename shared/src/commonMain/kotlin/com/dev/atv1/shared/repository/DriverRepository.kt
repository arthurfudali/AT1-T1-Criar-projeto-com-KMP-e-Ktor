package com.dev.atv1.shared.repository

import com.dev.atv1.shared.model.CreateDriverRequest
import com.dev.atv1.shared.model.Driver
import com.dev.atv1.shared.model.UpdateDriverRequest

interface DriverRepository {
    suspend fun findAll(): List<Driver>
    suspend fun findById(id: Int): Driver?
    suspend fun create(input: CreateDriverRequest): Driver
    suspend fun update(id: Int, input: UpdateDriverRequest): Driver?
    suspend fun delete(id: Int): Boolean
}
