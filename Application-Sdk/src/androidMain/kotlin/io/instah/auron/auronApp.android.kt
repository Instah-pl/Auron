package io.instah.auron

import androidx.compose.runtime.Composable

actual fun auronApp(
    title: String,
    ui: @Composable () -> Unit
) {
    if (AuronRuntimeAppManager.initSetContentLambda == null) throw Exception("UI initialization not available")
    AuronRuntimeAppManager.initSetContentLambda?.invoke({
        FrameworkAppView {
            ui()
        }
    })
}
