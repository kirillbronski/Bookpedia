package com.kbcoding.cmp.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.kbcoding.cmp.bookpedia.app.App
import com.kbcoding.cmp.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }