package pl.instah.auron.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Center(
    content: @Composable BoxScope.() -> Unit
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    content()
}