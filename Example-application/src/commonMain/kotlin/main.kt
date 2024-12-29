package com.example

import AppManager
import AppManager.counter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.instah.auron.auronApp
import pl.instah.auron.ui.Center

fun main() = auronApp("Example") {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Center {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Example app",
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = { AppManager.click() }
                    ) {
                        Text(counter.toString())
                    }
                }
            }
        }
    }
}