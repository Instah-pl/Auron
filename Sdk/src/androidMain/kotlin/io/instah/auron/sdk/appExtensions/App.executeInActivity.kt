package io.instah.auron.sdk.appExtensions

import androidx.activity.ComponentActivity
import io.instah.auron.sdk.App
import io.instah.auron.sdk.AuronRuntimeManager
import io.instah.auron.sdk.runtimeManager.executeInActivity

@Suppress("UNCHECKED_CAST")
fun <T> App.executeInActivity(
    block: ComponentActivity.() -> T
): T = AuronRuntimeManager.executeInActivity!!(
    block as (ComponentActivity.() -> Unit)
) as T