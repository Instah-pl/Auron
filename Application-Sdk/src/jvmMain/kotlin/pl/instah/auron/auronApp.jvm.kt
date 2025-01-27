package pl.instah.auron

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.event.FocusEvent
import java.awt.event.FocusListener

actual fun auronApp(
    title: String, ui: @Composable (() -> Unit)
) = application {
    Window(onCloseRequest = ::exitApplication, title = title) {
        LaunchedEffect(Unit) {
            AuronRuntimeManager.quitApp = ::exitApplication

            window.addFocusListener(
                object : FocusListener {
                    override fun focusGained(e: FocusEvent?) {
                        App.Callbacks.resume.registered.forEach {
                            it.value()
                        }
                    }

                    override fun focusLost(e: FocusEvent?) {}
                }
            )
        }

        ui()
    }
}
