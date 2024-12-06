package pl.instah.auron

import androidx.compose.runtime.Composable

fun auronApp(
    ui: @Composable () -> Unit
) {
    if (AuronRuntimeAppManager.initSetContentLambda == null) throw Exception("UI initialization not available")
    AuronRuntimeAppManager.initSetContentLambda?.invoke(ui)
}