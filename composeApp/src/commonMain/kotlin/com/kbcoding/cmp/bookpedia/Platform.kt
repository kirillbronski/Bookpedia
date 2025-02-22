package com.kbcoding.cmp.bookpedia

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform