package com.dev.atv1.db.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RaceResultsTable : Table("race_results") {
    val id = integer("id").autoIncrement()
    val season = integer("season")
    val grandPrix = varchar("grand_prix", 120)
    val position = integer("position")
    val points = integer("points")
    val driverId = integer("driver_id").references(DriversTable.id, onDelete = ReferenceOption.RESTRICT)

    override val primaryKey = PrimaryKey(id)
}
