package io.instah.auron.appSdk

import androidx.compose.runtime.Composable

expect fun auronApp(
    title: String = "Application",
    ui: @Composable () -> Unit
)
