package pl.instah.auron.interaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import pl.instah.auron.App
import java.util.UUID

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