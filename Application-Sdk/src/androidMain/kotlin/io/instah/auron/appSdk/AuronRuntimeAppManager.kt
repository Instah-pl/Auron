package io.instah.auron.appSdk

import androidx.compose.runtime.Composable
import kotlinx.coroutines.sync.Mutex

object AuronRuntimeAppManager {
    var initSetContentLambda: ((@Composable () -> Unit) -> Unit)? = null
    internal val permissionDataPreferencesMutex = Mutex()
}