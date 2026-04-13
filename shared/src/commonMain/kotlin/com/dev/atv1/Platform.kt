package com.dev.atv1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform