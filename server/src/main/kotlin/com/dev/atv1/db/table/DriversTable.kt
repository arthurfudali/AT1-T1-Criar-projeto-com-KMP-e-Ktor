package com.dev.atv1.db.table

import org.jetbrains.exposed.sql.Table

object DriversTable : Table("drivers") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 120)
    val team = varchar("team", 120)
    val nationality = varchar("nationality", 80)

    override val primaryKey = PrimaryKey(id)
}
