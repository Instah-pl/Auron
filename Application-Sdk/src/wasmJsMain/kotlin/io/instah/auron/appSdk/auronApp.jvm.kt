package io.instah.auron.appSdk

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

//TODO: Add Api for changing window title
@OptIn(ExperimentalComposeUiApi::class)
actual fun auronApp(
    title: String, ui: @Composable (() -> Unit)
) = CanvasBasedWindow(title) {
    FrameworkAppView {
        ui()
    }
}
