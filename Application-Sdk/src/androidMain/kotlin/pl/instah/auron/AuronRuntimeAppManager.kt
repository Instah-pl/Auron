package pl.instah.auron

import androidx.compose.runtime.Composable

object AuronRuntimeAppManager {
    var initSetContentLambda: ((@Composable () -> Unit) -> Unit)? = null
}