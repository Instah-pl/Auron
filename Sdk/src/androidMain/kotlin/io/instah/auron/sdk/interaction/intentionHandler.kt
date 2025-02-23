package io.instah.auron.sdk.interaction

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import io.instah.auron.sdk.App
import io.instah.auron.sdk.appExtensions.executeInActivity
import io.instah.auron.sdk.compose.rememberSaveable

//TODO: Serializable intentions with fields
//TODO: Support on desktop (for ex. opening links in app)
@Composable
fun intentionHandler(
    intentionHandlerSelector: IntentionHandlerSelector = IntentionHandlerSelector(),
    block: (Map<String, String>) -> Unit
) {
    var uuid: String? by rememberSaveable { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (intentionHandlerSelector.initialIntention) {
            val values = App.executeInActivity {
                val keys = intent.extras?.keySet() ?: setOf()
                keys.associate {
                    it to intent.getStringExtra(it)!!
                }
            }

            if (values.isNotEmpty()) {
                block(values)
            }
        }

        if (intentionHandlerSelector.newIntentions) {
            uuid = App.Callbacks.newIntention.register { block(it) }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (intentionHandlerSelector.newIntentions) {
                coroutineScope.launch {
                    App.Callbacks.newIntention.remove(uuid!!)
                }
            }
        }
    }
}