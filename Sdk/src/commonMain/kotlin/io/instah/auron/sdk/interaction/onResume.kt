package io.instah.auron.sdk.interaction

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.launch
import io.instah.auron.sdk.App

@Composable
fun onResume(
    action: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var uuid by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        uuid = App.Callbacks.resume.register {
            action()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            uuid?.let { uuidNotNull ->
                coroutineScope.launch {
                    App.Callbacks.resume.remove(uuidNotNull)
                }
            }
        }
    }
}