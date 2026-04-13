package com.dev.atv1.db

import com.dev.atv1.config.AppEnvironment
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

object DatabaseFactory {
    fun create(environment: AppEnvironment): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = environment.jdbcUrl
            username = environment.dbUser
            password = environment.dbPassword
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 5
            isAutoCommit = true
            validate()
        }
        return HikariDataSource(config).also { Database.connect(it) }
    }

    fun migrate(dataSource: DataSource) {
        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .load()
            .migrate()
    }
}
