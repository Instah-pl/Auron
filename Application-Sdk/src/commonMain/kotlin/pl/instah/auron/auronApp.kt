package pl.instah.auron

import androidx.compose.runtime.Composable

expect fun auronApp(
    title: String = "Application",
    ui: @Composable () -> Unit
)
