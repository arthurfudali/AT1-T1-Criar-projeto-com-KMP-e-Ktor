package com.dev.atv1.repository

import com.dev.atv1.db.table.RaceResultsTable
import com.dev.atv1.shared.model.CreateRaceResultRequest
import com.dev.atv1.shared.model.RaceResult
import com.dev.atv1.shared.model.UpdateRaceResultRequest
import com.dev.atv1.shared.repository.RaceResultRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedRaceResultRepository : RaceResultRepository {
    override suspend fun findAll(): List<RaceResult> = transaction {
        RaceResultsTable.selectAll()
            .orderBy(RaceResultsTable.id to SortOrder.ASC)
            .map { it.toRaceResult() }
    }

    override suspend fun findById(id: Int): RaceResult? = transaction {
        RaceResultsTable.selectAll()
            .where { RaceResultsTable.id eq id }
            .singleOrNull()
            ?.toRaceResult()
    }

    override suspend fun create(input: CreateRaceResultRequest): RaceResult = transaction {
        val row = RaceResultsTable.insert {
            it[season] = input.season
            it[grandPrix] = input.grandPrix
            it[position] = input.position
            it[points] = input.points
            it[driverId] = input.driverId
        }.resultedValues!!.single()
        row.toRaceResult()
    }

    override suspend fun update(id: Int, input: UpdateRaceResultRequest): RaceResult? = transaction {
        val updatedRows = RaceResultsTable.update({ RaceResultsTable.id eq id }) {
            it[season] = input.season
            it[grandPrix] = input.grandPrix
            it[position] = input.position
            it[points] = input.points
            it[driverId] = input.driverId
        }
        if (updatedRows == 0) {
            null
        } else {
            RaceResultsTable.selectAll()
                .where { RaceResultsTable.id eq id }
                .single()
                .toRaceResult()
        }
    }

    override suspend fun delete(id: Int): Boolean = transaction {
        RaceResultsTable.deleteWhere { RaceResultsTable.id eq id } > 0
    }
}

private fun ResultRow.toRaceResult() = RaceResult(
    id = this[RaceResultsTable.id],
    season = this[RaceResultsTable.season],
    grandPrix = this[RaceResultsTable.grandPrix],
    position = this[RaceResultsTable.position],
    points = this[RaceResultsTable.points],
    driverId = this[RaceResultsTable.driverId],
)
