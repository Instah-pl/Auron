package io.instah.auron.sdk.compose

import androidx.compose.runtime.Composable

@Composable
expect fun <T : Any> rememberSaveable(
    init: () -> T
): T