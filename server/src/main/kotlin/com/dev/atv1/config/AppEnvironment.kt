package com.dev.atv1.config

import io.github.cdimascio.dotenv.dotenv

data class AppEnvironment(
    val serverPort: Int,
    val dbHost: String,
    val dbPort: Int,
    val dbName: String,
    val dbUser: String,
    val dbPassword: String,
) {
    val jdbcUrl: String
        get() = "jdbc:postgresql://$dbHost:$dbPort/$dbName"

    companion object {
        fun load(): AppEnvironment {
            val env = dotenv {
                ignoreIfMissing = true
            }

            fun value(key: String, default: String): String =
                System.getenv(key) ?: env[key] ?: default

            return AppEnvironment(
                serverPort = value("SERVER_PORT", "8080").toInt(),
                dbHost = value("DB_HOST", "localhost"),
                dbPort = value("DB_PORT", "5432").toInt(),
                dbName = value("DB_NAME", "atv1"),
                dbUser = value("DB_USER", "atv1_user"),
                dbPassword = value("DB_PASSWORD", "atv1_pass"),
            )
        }
    }
}
