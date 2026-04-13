package com.dev.atv1.repository

import com.dev.atv1.db.table.DriversTable
import com.dev.atv1.shared.model.CreateDriverRequest
import com.dev.atv1.shared.model.Driver
import com.dev.atv1.shared.model.UpdateDriverRequest
import com.dev.atv1.shared.repository.DriverRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExposedDriverRepository : DriverRepository {
    override suspend fun findAll(): List<Driver> = transaction {
        DriversTable.selectAll()
            .orderBy(DriversTable.id to SortOrder.ASC)
            .map { it.toDriver() }
    }

    override suspend fun findById(id: Int): Driver? = transaction {
        DriversTable.selectAll()
            .where { DriversTable.id eq id }
            .singleOrNull()
            ?.toDriver()
    }

    override suspend fun create(input: CreateDriverRequest): Driver = transaction {
        val row = DriversTable.insert {
            it[name] = input.name
            it[team] = input.team
            it[nationality] = input.nationality
        }.resultedValues!!.single()
        row.toDriver()
    }

    override suspend fun update(id: Int, input: UpdateDriverRequest): Driver? = transaction {
        val updatedRows = DriversTable.update({ DriversTable.id eq id }) {
            it[name] = input.name
            it[team] = input.team
            it[nationality] = input.nationality
        }
        if (updatedRows == 0) {
            null
        } else {
            DriversTable.selectAll()
                .where { DriversTable.id eq id }
                .single()
                .toDriver()
        }
    }

    override suspend fun delete(id: Int): Boolean = transaction {
        DriversTable.deleteWhere { DriversTable.id eq id } > 0
    }
}

private fun ResultRow.toDriver() = Driver(
    id = this[DriversTable.id],
    name = this[DriversTable.name],
    team = this[DriversTable.team],
    nationality = this[DriversTable.nationality],
)
