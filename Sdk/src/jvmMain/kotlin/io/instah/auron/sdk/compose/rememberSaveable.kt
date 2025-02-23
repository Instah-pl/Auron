package io.instah.auron.sdk.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
actual fun <T : Any> rememberSaveable(
    init: () -> T
): T = rememberSaveable(
    init = init
)