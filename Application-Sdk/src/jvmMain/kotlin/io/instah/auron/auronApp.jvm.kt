package io.instah.auron

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.instah.auron.sdk.App
import io.instah.auron.sdk.AuronRuntimeManager
import io.instah.auron.sdk.language.Language
import io.instah.auron.sdk.language.TranslationManager
import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import java.util.Locale

actual fun auronApp(
    title: String, ui: @Composable (() -> Unit)
) = application {
    Window(onCloseRequest = ::exitApplication, title = title) {
        LaunchedEffect(Unit) {
            Language.entries.firstOrNull {
                it.name == Locale.getDefault().language+"_"+Locale.getDefault().country
            }?.let { language ->
                TranslationManager.setLanguage(language)
            }

            AuronRuntimeManager.quitApp = ::exitApplication

            window.addFocusListener(
                object : FocusListener {
                    override fun focusGained(e: FocusEvent?) {
                        println("hi!")
                        App.Callbacks.resume.registered.forEach {
                            it.value()
                        }
                    }

                    override fun focusLost(e: FocusEvent?) {}
                }
            )
        }

        FrameworkAppView {
            ui()
        }
    }
}
