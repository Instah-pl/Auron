package pl.instah.auron

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

actual fun auronApp(
    title: String, ui: @Composable (() -> Unit)
) = application {
    Window(onCloseRequest = ::exitApplication, title = title) {
        LaunchedEffect(Unit) {
            AuronRuntimeManager.quitApp = ::exitApplication
        }

        ui()
    }
}
