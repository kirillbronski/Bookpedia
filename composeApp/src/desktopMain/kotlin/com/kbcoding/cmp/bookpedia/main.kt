package com.kbcoding.cmp.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kbcoding.cmp.bookpedia.app.App
import com.kbcoding.cmp.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookpedia",
        ) {
            App()
        }
    }
}