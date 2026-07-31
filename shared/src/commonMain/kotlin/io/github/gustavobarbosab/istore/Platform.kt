package io.github.gustavobarbosab.istore

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform