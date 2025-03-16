@file:Suppress("DEPRECATION")

package com.example

import AppManager
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.instah.auron.appSdk.auronApp
import io.instah.auron.sdk.App
import io.instah.auron.sdk.interaction.onResume
import io.instah.auron.sdk.ui.Center
import kotlinx.coroutines.launch

fun main() = auronApp("Example") {
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            println("hi!")
        }
    }

    onResume {
        println("On resume working!")
    }

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(Modifier.fillMaxSize()) {
            Center {
                AnimatedContent(
                    targetState = AppManager.state > 0,
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) { state ->
                    if (state) {
                        Center {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text("Permission granted")

                                Button(
                                    onClick = { App.quit() }
                                ) {
                                    Text(ExampleTranslationScheme.exampleTranslationSchemeHolder.quit)
                                }

                                if (AppManager.state != 2) {
                                    Button(
                                        onClick = {
                                            coroutineScope.launch {
                                                AppManager.escalateGrantPermission()
                                            }
                                        }
                                    ) {
                                        Text("Escalate")
                                    }
                                }
                            }
                        }
                    } else {
                        Button(
                            onClick = {
                                coroutineScope.launch { AppManager.attemptGrantPermission() }
                            }
                        ) {
                            Text("Grant Permission")
                        }
                    }
                }
            }
        }
    }
}